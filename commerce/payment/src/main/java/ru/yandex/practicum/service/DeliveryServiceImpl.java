package ru.yandex.practicum.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.dto.order.OrderDto;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class DeliveryServiceImpl implements DeliveryService {
    @Override
    public DeliveryDto planDelivery(DeliveryDto deliveryDto) {
        return null;
    }

    @Override
    public BigDecimal deliveryCost(OrderDto orderDto) {
        return null;
    }

    @Override
    public void deliveryPicked(UUID orderId) {

    }

    @Override
    public void deliverySuccessful(UUID orderId) {

    }

    @Override
    public void deliveryFailed(UUID orderId) {

    }
}
