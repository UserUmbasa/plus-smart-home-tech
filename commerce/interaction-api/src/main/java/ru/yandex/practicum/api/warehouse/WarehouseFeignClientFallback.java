package ru.yandex.practicum.api.warehouse;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.shoppingCart.ShoppingCartDto;
import ru.yandex.practicum.dto.warehouse.AddProductToWarehouseRequest;
import ru.yandex.practicum.dto.warehouse.AddressDto;
import ru.yandex.practicum.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.dto.warehouse.NewProductInWarehouseRequest;


@Component
public class WarehouseFeignClientFallback implements WarehouseClient {


    @Override
    public void newProductInWarehouse(NewProductInWarehouseRequest request) {

    }

    @Override
    public BookedProductsDto checkProductQuantityEnoughForShoppingCart(ShoppingCartDto cartDto) {
        BookedProductsDto error = new BookedProductsDto();
        error.setError("Склад недоступен");
        return error;
    }

    @Override
    public void addProductToWarehouse(AddProductToWarehouseRequest request) {

    }

    @Override
    public AddressDto getWarehouseAddress() {
        return null;
    }

}

