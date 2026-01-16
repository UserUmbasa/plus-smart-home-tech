package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.order.OrderRequestDto;
import ru.yandex.practicum.dto.order.OrderResponseDto;

public interface OrderService {
    OrderResponseDto newOrder(OrderRequestDto request);

//    BookedProductsDto checkProductQuantityEnoughForShoppingCart(ShoppingCartDto cartDto);
//
//    void addProductToWarehouse(AddProductToWarehouseRequest request);
//
//    AddressDto getWarehouseAddress();
}
