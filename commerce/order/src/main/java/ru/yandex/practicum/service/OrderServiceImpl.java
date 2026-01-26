package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.api.delivery.DeliveryOperations;
import ru.yandex.practicum.api.payment.PaymentOperations;
import ru.yandex.practicum.api.shoppingCart.ShoppingCartOperations;
import ru.yandex.practicum.api.warehouse.WarehouseClient;
import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.dto.delivery.DeliveryState;
import ru.yandex.practicum.dto.order.OrderRequestDto;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.order.OrderState;
import ru.yandex.practicum.dto.order.ProductReturnRequest;
import ru.yandex.practicum.dto.payment.PaymentDto;
import ru.yandex.practicum.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.exception.NoOrderFoundException;
import ru.yandex.practicum.exception.NotAuthorizedUserException;
import ru.yandex.practicum.mapper.OrderMapper;
import ru.yandex.practicum.model.Order;
import ru.yandex.practicum.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final WarehouseClient warehouseClient;
    private final ShoppingCartOperations shoppingCartClient;
    private final DeliveryOperations deliveryOperations;
    private final PaymentOperations paymentOperations;
    private final OrderRepository orderRepository;
    private final OrderMapper mapper;

    @Transactional
    @Override
    public OrderDto newOrder(OrderRequestDto request) {
        // проверили наличие на складе
        BookedProductsDto bookedProductsDto = warehouseClient.checkProductQuantityEnoughForShoppingCart(request.getShoppingCart());
        // проверили корзину юзера (тема авторизация в ФЗ практически не раскрыта)
        String username = shoppingCartClient.getUsernameById(request.getShoppingCart().getCartId());
        Order order = mapper.mapToOrder(request, bookedProductsDto, username);
        order = orderRepository.save(order);
        // доставка
        DeliveryDto delivery = new DeliveryDto();
        delivery.setFromAddress(warehouseClient.getWarehouseAddress());
        delivery.setToAddress(request.getAddress());
        delivery.setOrderId(order.getOrderId());
        delivery.setDeliveryState(DeliveryState.CREATED);
        delivery = deliveryOperations.planDelivery(delivery);
        order.setDeliveryId(delivery.getDeliveryId());
        order = orderRepository.save(order);
        log.info("Заказ и Доставка успешно созданы");
        return mapper.mapToOrderDto(order);
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderDto> getClientOrders(String username) {
        validateUsername(username);
        List<Order> result = orderRepository.findAllByUsername(username);
        log.debug("Вернули список заказов - {}", result);
        return result.stream().
                map(mapper::mapToOrderDto).
                toList();
    }

    @Transactional
    @Override
    public OrderDto productReturn(ProductReturnRequest request) {
        // возврат на склад
        warehouseClient.acceptReturn(request.getProducts());
        return updateOrderState(request.getOrderId(), OrderState.PRODUCT_RETURNED
                ,"Вернули на склад, поменяли статус заказа");
    }

    @Transactional
    @Override
    public OrderDto payment(UUID orderId) {
        return updateOrderState(orderId, OrderState.PAID
                ,"Заказ оплачен");
    }

    @Transactional
    @Override
    public OrderDto paymentFailed(UUID orderId) {
        return updateOrderState(orderId, OrderState.PAYMENT_FAILED
                ,"Оплата заказа не удалась");
    }

    @Transactional
    @Override
    public OrderDto delivery(UUID orderId) {
        return updateOrderState(orderId, OrderState.DELIVERED
                ,"Успешная доставка заказа");
    }

    @Transactional
    @Override
    public OrderDto deliveryFailed(UUID orderId) {
        return updateOrderState(orderId, OrderState.DELIVERY_FAILED
                ,"Не успешная доставка заказа");
    }

    @Transactional
    @Override
    public OrderDto complete(UUID orderId) {
        return updateOrderState(orderId, OrderState.COMPLETED
                ,"Заказ завершен");
    }

    @Transactional
    @Override
    public OrderDto calculateTotalCost(UUID orderId) {
        Order order = getOrderById(orderId);
        BigDecimal totalCost = paymentOperations.getTotalCost(mapper.mapToOrderDto(order));
        order.setTotalPrice(totalCost);

        PaymentDto paymentDto =  paymentOperations.payment(mapper.mapToOrderDto(order));
        order.setPaymentId(paymentDto.getPaymentId());

        order = orderRepository.save(order);
        log.info("Вычислили и задали общую стоимость заказа - {}", order);
        return mapper.mapToOrderDto(order);
    }

    @Transactional
    @Override
    public OrderDto calculateDeliveryCost(UUID orderId) {
        Order order = getOrderById(orderId);
        BigDecimal deliveryCost = deliveryOperations.deliveryCost(mapper.mapToOrderDto(order));
        order.setDeliveryPrice(deliveryCost);
        order = orderRepository.save(order);
        log.info("Вычислили и задали стоимость доставки заказа - {}", order);
        return mapper.mapToOrderDto(order);
    }

    @Transactional
    @Override // Вызывает сторонний сервис (Delivery)
    public OrderDto assembly(UUID orderId) {
        return updateOrderState(orderId, OrderState.ASSEMBLED
                ,"Успешная сборка заказа");
    }

    @Transactional
    @Override
    public OrderDto assemblyFailed(UUID orderId) {
        return updateOrderState(orderId, OrderState.ASSEMBLY_FAILED
                ,"Не успешная сборка заказа");
    }

    //----------------------------------------------------------------------------

    private void validateUsername(String username) {
        if (username.isBlank()) {
            throw new NotAuthorizedUserException(username);
        }
    }

    private Order getOrderById(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException("Такого заказа нет в базе: " + orderId));
    }

    private OrderDto updateOrderState(UUID orderId, OrderState newState, String logMessage) {
        Order order = getOrderById(orderId);
        order.setOrderState(newState);
        order = orderRepository.save(order);
        log.info(logMessage);
        return mapper.mapToOrderDto(order);
    }
}
