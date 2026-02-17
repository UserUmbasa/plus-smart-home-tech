package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.api.order.OrderOperations;
import ru.yandex.practicum.dto.order.OrderRequestDto;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.order.ProductReturnRequest;
import ru.yandex.practicum.service.OrderService;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController implements OrderOperations {
    private final OrderService orderService;

    @Override // PUT /api/v1/order - Создать новый заказ в системе
    public OrderDto createNewOrder(OrderRequestDto orderRequestDto) {
        return orderService.newOrder(orderRequestDto);
    }

    @Override // GET /api/v1/order - Получить заказы пользователя
    public List<OrderDto> getClientOrders(String username) {
        return orderService.getClientOrders(username);
    }

    @Override // POST /api/v1/order/return - Возврат заказа
    public OrderDto productReturn(ProductReturnRequest request) {
        return orderService.productReturn(request);
    }

    @Override // POST /api/v1/order/payment - Оплата заказа
    public OrderDto payment(UUID orderId) {
        return orderService.payment(orderId);
    }

    @Override // POST /api/v1/order/payment/failed - Оплата заказа произошла с ошибкой
    public OrderDto paymentFailed(UUID orderId) {
        return orderService.paymentFailed(orderId);
    }

    @Override // POST /api/v1/order/delivery - Доставка заказа
    public OrderDto delivery(UUID orderId) {
        return orderService.delivery(orderId);
    }

    @Override // POST /api/v1/order/delivery/failed - Доставка заказа произошла с ошибкой
    public OrderDto deliveryFailed(UUID orderId) {
        return orderService.deliveryFailed(orderId);
    }

    @Override // POST /api/v1/order/completed - Завершение заказа
    public OrderDto complete(UUID orderId) {
        return orderService.complete(orderId);
    }

    @Override // POST /api/v1/order/calculate/total - Расчёт стоимости заказа
    public OrderDto calculateTotalCost(UUID orderId) {
        return orderService.calculateTotalCost(orderId);
    }

    @Override // POST /api/v1/order/calculate/delivery - Расчёт стоимости доставки заказа
    public OrderDto calculateDeliveryCost(UUID orderId) {
        return orderService.calculateDeliveryCost(orderId);
    }

    @Override // POST /api/v1/order/assembly - Сборка заказа
    public OrderDto assembly(UUID orderId) {
        return orderService.assembly(orderId);
    }

    @Override // POST /api/v1/order/assembly/failed - Сборка заказа произошла с ошибкой
    public OrderDto assemblyFailed(UUID orderId) {
        return orderService.assemblyFailed(orderId);
    }

}
