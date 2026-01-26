package ru.yandex.practicum.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.api.order.OrderOperations;
import ru.yandex.practicum.api.warehouse.WarehouseClient;
import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.dto.delivery.DeliveryState;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.warehouse.ShippedToDeliveryRequest;
import ru.yandex.practicum.exception.NoDeliveryFoundException;
import ru.yandex.practicum.mapper.DeliveryMapper;
import ru.yandex.practicum.model.Delivery;
import ru.yandex.practicum.repository.DeliveryRepository;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final DeliveryMapper deliveryMapper;
    private final WarehouseClient warehouseClient;
    private final OrderOperations orderOperations;

    private static final BigDecimal BASE_RATE = BigDecimal.valueOf(5.0);
    private static final BigDecimal WAREHOUSE_ADDRESS_MULTIPLIER = BigDecimal.valueOf(2);
    private static final BigDecimal FRAGILE_MULTIPLIER = BigDecimal.valueOf(0.2);
    private static final BigDecimal WEIGHT_MULTIPLIER = BigDecimal.valueOf(0.3);
    private static final BigDecimal VOLUME_MULTIPLIER = BigDecimal.valueOf(0.2);

    @Transactional
    @Override
    public DeliveryDto planDelivery(DeliveryDto deliveryDto) {
        Delivery result = deliveryRepository.save(deliveryMapper.mapToDelivery(deliveryDto));
        log.info("Создали новую доставку с идентификатором: {}", result);
        return deliveryMapper.mapToDeliveryDto(result);
    }

    @Transactional
    @Override
    public BigDecimal deliveryCost(OrderDto orderDto) {
        BigDecimal totalCost = BASE_RATE;
        Delivery delivery = getDeliveryById(orderDto.getDeliveryId());
        // не учитываем дом, квартира
        if (!delivery.getFromAddress().equals(delivery.getToAddress())) {
            totalCost = totalCost.add(totalCost.multiply(WAREHOUSE_ADDRESS_MULTIPLIER));
        }
        if (orderDto.getFragile()) {
            totalCost = totalCost.add(totalCost.multiply(FRAGILE_MULTIPLIER));
        }
        totalCost = totalCost.add(BigDecimal.valueOf(orderDto.getDeliveryWeight()).multiply(WEIGHT_MULTIPLIER));
        totalCost = totalCost.add(BigDecimal.valueOf(orderDto.getDeliveryVolume()).multiply(VOLUME_MULTIPLIER));
        log.info("Расчёт полной стоимости доставки: {}", totalCost);
        return totalCost;
    }

    @Transactional
    @Override // Эмуляция получения товара в доставку.
    public void deliveryPicked(UUID deliveryId) {
        Delivery delivery = getDeliveryById(deliveryId);
        delivery.setDeliveryState(DeliveryState.IN_PROGRESS);
        // передали в доставку (забрали со склада)
        warehouseClient.shippedToDelivery(new ShippedToDeliveryRequest(deliveryId,delivery.getDeliveryId()));
        // обновили заказ на ASSEMBLED (в процессе сборки)
        orderOperations.assembly(delivery.getOrderId());
        log.info("Товар передан в доставку: deliveryId = {}", deliveryId);
    }

    @Transactional
    @Override // Эмуляция успешной доставки товара.
    public void deliverySuccessful(UUID deliveryId) {
        Delivery delivery = getDeliveryById(deliveryId);
        delivery.setDeliveryState(DeliveryState.DELIVERED);
        deliveryRepository.save(delivery);
        // обновили заказ на DELIVERED (доставлено)
        orderOperations.delivery(delivery.getOrderId());
        log.info("Успешная доставка товара: deliveryId = {}", deliveryId);
    }

    @Transactional
    @Override
    public void deliveryFailed(UUID deliveryId) {
        Delivery delivery = getDeliveryById(deliveryId);
        delivery.setDeliveryState(DeliveryState.FAILED);
        deliveryRepository.save(delivery);
        // обновили заказ на DELIVERY_FAILED (не доставлено)
        orderOperations.deliveryFailed(delivery.getOrderId());
        log.info("Не успешная доставка товара: deliveryId = {}", deliveryId);
    }

    // ---------------------------------------------------------------
    private Delivery getDeliveryById(UUID deliveryId) {
        return deliveryRepository.findByOrderId(deliveryId)
                .orElseThrow(() -> new NoDeliveryFoundException
                        ("Доставки для такого заказа не найдено: orderId = " + deliveryId));
    }
}
