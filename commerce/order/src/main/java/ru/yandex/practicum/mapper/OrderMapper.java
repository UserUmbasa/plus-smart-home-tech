package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.dto.order.OrderRequestDto;
import ru.yandex.practicum.dto.order.OrderResponseDto;
import ru.yandex.practicum.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.dto.warehouse.NewProductInWarehouseRequest;
import ru.yandex.practicum.model.Order;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    OrderResponseDto mapToResponseDto(Order order);

    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "products", source = "request.shoppingCart.products")
    @Mapping(target = "shoppingCartId", source = "request.shoppingCart.cartId")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "deliveryWeight", source = "bookedProductsDto.deliveryWeight")
    @Mapping(target = "deliveryVolume", source = "bookedProductsDto.deliveryVolume")
    @Mapping(target = "fragile", source = "bookedProductsDto.fragile")
    Order mapToOrder(OrderRequestDto request, BookedProductsDto bookedProductsDto, String username);

}
