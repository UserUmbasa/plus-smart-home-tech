package ru.yandex.practicum.api.warehouse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.shoppingCart.ShoppingCartDto;
import ru.yandex.practicum.dto.warehouse.AddProductToWarehouseRequest;
import ru.yandex.practicum.dto.warehouse.AddressDto;
import ru.yandex.practicum.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.dto.warehouse.NewProductInWarehouseRequest;
import ru.yandex.practicum.exception.WarehouseServiceException;



@Slf4j
@Component
public class WarehouseFeignClientFallback implements WarehouseClient {

    @Override
    public void newProductInWarehouse(NewProductInWarehouseRequest request) {
        log.error("Не удалось добавить новый продукт на склад. Склад недоступен");
        throw new WarehouseServiceException("Временная недоступность сервиса склада");
    }

    @Override
    public BookedProductsDto checkProductQuantityEnoughForShoppingCart(ShoppingCartDto cartDto) {
        log.error("Не удалось проверить количество товаров. Склад недоступен");
        throw new WarehouseServiceException("Временная недоступность сервиса склада");
    }

    @Override
    public void addProductToWarehouse(AddProductToWarehouseRequest request) {
        log.error("Не удалось добавить товар на склад. Склад недоступен");
        throw new WarehouseServiceException("Временная недоступность сервиса склада");
    }

    @Override
    public AddressDto getWarehouseAddress() {
        log.error("Не удалось получить адрес склада. Склад недоступен");
        throw new WarehouseServiceException("Временная недоступность сервиса склада");
    }
}


