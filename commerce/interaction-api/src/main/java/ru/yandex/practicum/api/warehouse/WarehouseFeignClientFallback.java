package ru.yandex.practicum.api.warehouse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.shoppingCart.ShoppingCartDto;
import ru.yandex.practicum.dto.warehouse.*;
import ru.yandex.practicum.exception.WarehouseServiceException;

import java.util.Map;
import java.util.UUID;


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

    @Override
    public BookedProductsDto assemblyProductsForOrder(AssemblyProductsForOrderRequest request) {
        log.error("Не собрать товары к заказу для подготовки к отправке. Склад недоступен");
        throw new WarehouseServiceException("Временная недоступность сервиса склада");
    }

    @Override
    public void shippedToDelivery(ShippedToDeliveryRequest request) {
        log.error("Не удалось передать товары в доставку. Склад недоступен");
        throw new WarehouseServiceException("Временная недоступность сервиса склада");
    }

    @Override
    public void acceptReturn(Map<UUID, Integer> productsToReturn) {
        log.error("Не удалось принять возврат товаров на склад. Склад недоступен");
        throw new WarehouseServiceException("Временная недоступность сервиса склада");
    }
}


