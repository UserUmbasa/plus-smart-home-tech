package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.api.delivery.DeliveryOperations;
import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.service.DeliveryService;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/delivery")
public class DeliveryController implements DeliveryOperations {
    private final DeliveryService deliveryService;

    @Override // PUT /api/v1/delivery - Создать новую доставку в БД
    public DeliveryDto planDelivery(DeliveryDto deliveryDto) {
        return deliveryService.planDelivery(deliveryDto);
    }

    @Override // POST /api/v1/delivery/cost - Расчёт полной стоимости доставки заказа
    public BigDecimal deliveryCost(OrderDto orderDto) {
        return deliveryService.deliveryCost(orderDto);
    }

    @Override // POST /api/v1/delivery/picked - Эмуляция получения товара в доставку
    public void deliveryPicked(UUID orderId) {
        deliveryService.deliveryPicked(orderId);
    }

    @Override // POST /api/v1/delivery/successful - Эмуляция успешной доставки товара
    public void deliverySuccessful(UUID orderId) {
        deliveryService.deliverySuccessful(orderId);
    }

    @Override // POST /api/v1/delivery/failed - Эмуляция неудачного вручения товара
    public void deliveryFailed(UUID orderId) {
        deliveryService.deliveryFailed(orderId);
    }
}
