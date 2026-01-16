package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.dto.order.OrderRequestDto;
import ru.yandex.practicum.dto.order.OrderResponseDto;
import ru.yandex.practicum.dto.warehouse.NewProductInWarehouseRequest;
import ru.yandex.practicum.model.Order;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    OrderResponseDto mapToResponseDto(Order order);

    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "products", source = "request.shoppingCart.products")
    @Mapping(target = "shoppingCartId", source = "request.shoppingCart.cartId")
//    @Mapping(target = "username", source = "username")
//    @Mapping(target = "deliveryWeight", source = "bookedProductsDto.deliveryWeight")
//    @Mapping(target = "deliveryVolume", source = "bookedProductsDto.deliveryVolume")
//    @Mapping(target = "fragile", source = "bookedProductsDto.fragile")
    Order mapToOrder(OrderRequestDto request);

//    {
//        "shoppingCart": {
//        "shoppingCartId": "53aa35c8-e659-44b2-882f-f6056e443c99",
//                "products": {
//            "additionalProp1": 0,
//                    "additionalProp2": 0,
//                    "additionalProp3": 0
//        }
//    },
//        "deliveryAddress": {
//        "country": "string",
//                "city": "string",
//                "street": "string",
//                "house": "string",
//                "flat": "string"
//    }
//    }

}
