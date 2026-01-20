package ru.yandex.practicum.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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

    private static final BigDecimal BASE_RATE = BigDecimal.valueOf(5.0);
    private static final BigDecimal WAREHOUSE_1_ADDRESS_MULTIPLIER = BigDecimal.valueOf(1);
    private static final BigDecimal WAREHOUSE_2_ADDRESS_MULTIPLIER = BigDecimal.valueOf(2);
    private static final BigDecimal FRAGILE_MULTIPLIER = BigDecimal.valueOf(0.2);
    private static final BigDecimal WEIGHT_MULTIPLIER = BigDecimal.valueOf(0.3);
    private static final BigDecimal VOLUME_MULTIPLIER = BigDecimal.valueOf(0.2);
    private static final BigDecimal STREET_MULTIPLIER = BigDecimal.valueOf(0.2);

    @Transactional
    @Override // Создать новую доставку в БД
    public DeliveryDto planDelivery(DeliveryDto deliveryDto) {
        Delivery result = deliveryRepository.save(deliveryMapper.mapToDelivery(deliveryDto));
        return deliveryMapper.mapToDeliveryDto(result);
    }

    @Override // Расчёт полной стоимости доставки заказа
    public BigDecimal deliveryCost(OrderDto orderDto) {
        BigDecimal totalCost = BASE_RATE;
        Delivery delivery = deliveryRepository.findById(orderDto.getDeliveryId())
                .orElseThrow(() -> new NoDeliveryFoundException
                        ("Такой доставки не найдено: deliveryId = " + orderDto.getDeliveryId()));
        // не учитываем дом, квартира
        if (!delivery.getFromAddress().equals(delivery.getToAddress())) {
            totalCost = totalCost.add(totalCost.multiply(WAREHOUSE_2_ADDRESS_MULTIPLIER));
        }
        if (orderDto.getFragile()) {
            totalCost = totalCost.add(totalCost.multiply(FRAGILE_MULTIPLIER));
        }
        totalCost = totalCost.add(BigDecimal.valueOf(orderDto.getDeliveryWeight()).multiply(WEIGHT_MULTIPLIER));
        totalCost = totalCost.add(BigDecimal.valueOf(orderDto.getDeliveryVolume()).multiply(VOLUME_MULTIPLIER));
        log.info("Возвращаем стоимость доставки");
        return totalCost;
    }

    @Override // !!!!!!!!!!!!
    public void deliveryPicked(UUID orderId) {
        Delivery delivery = deliveryRepository.findByOrderId(orderId)
                .orElseThrow(() -> new NoDeliveryFoundException
                        ("Доставки для такого заказа не найдено: orderId = " + orderId));
        delivery.setDeliveryState(DeliveryState.IN_PROGRESS);
        // передали в доставку
        warehouseClient.shippedToDelivery(new ShippedToDeliveryRequest(orderId,delivery.getDeliveryId()));
        // Также необходимо изменить статус заказа на ASSEMBLED
    }

    @Override
    public void deliverySuccessful(UUID orderId) {
        System.err.println("true");
    }

    @Override
    public void deliveryFailed(UUID orderId) {

    }
}
