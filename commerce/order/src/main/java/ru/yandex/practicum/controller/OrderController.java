package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.api.order.OrderClient;
import ru.yandex.practicum.dto.order.OrderRequestDto;
import ru.yandex.practicum.dto.order.OrderResponseDto;
import ru.yandex.practicum.service.OrderService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController implements OrderClient {
    private final OrderService orderService;

    @Override
    public OrderResponseDto createNewOrder(OrderRequestDto orderRequestDto) {
        return orderService.newOrder(orderRequestDto);
    }

//    @Override
//    public void newOre(NewProductInWarehouseRequest request) {
//        orderService.newProductInWarehouse(request);
//    }
//
//    @Override
//    public BookedProductsDto checkProductQuantityEnoughForShoppingCart(ShoppingCartDto cartDto) {
//        return orderService.checkProductQuantityEnoughForShoppingCart(cartDto);
//    }
//
//    @Override
//    public void addProductToWarehouse(AddProductToWarehouseRequest request) {
//        orderService.addProductToWarehouse(request);
//    }
//
//    @Override
//    public AddressDto getWarehouseAddress() {
//        return orderService.getWarehouseAddress();
//    }
}
