package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.api.delivery.DeliveryOperations;
import ru.yandex.practicum.api.shoppingCart.ShoppingCartOperations;
import ru.yandex.practicum.api.warehouse.WarehouseClient;
import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.dto.delivery.DeliveryState;
import ru.yandex.practicum.dto.order.OrderRequestDto;
import ru.yandex.practicum.dto.order.OrderResponseDto;
import ru.yandex.practicum.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.mapper.OrderMapper;
import ru.yandex.practicum.model.Order;
import ru.yandex.practicum.repository.OrderRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final WarehouseClient warehouseClient;
    private final ShoppingCartOperations shoppingCartClient;
    private final DeliveryOperations deliveryOperations;
    private final OrderRepository orderRepository;
    private final OrderMapper mapper;

    @Override
    public OrderResponseDto newOrder(OrderRequestDto request) {
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

        return mapper.mapToResponseDto(order);
    }
}
