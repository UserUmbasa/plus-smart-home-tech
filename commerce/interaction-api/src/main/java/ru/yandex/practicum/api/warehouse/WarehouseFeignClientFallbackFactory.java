package ru.yandex.practicum.api.warehouse;

import feign.FeignException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.dto.shoppingCart.ShoppingCartDto;
import ru.yandex.practicum.dto.warehouse.AddProductToWarehouseRequest;
import ru.yandex.practicum.dto.warehouse.AddressDto;
import ru.yandex.practicum.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.dto.warehouse.NewProductInWarehouseRequest;
import ru.yandex.practicum.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.yandex.practicum.exception.WarehouseServiceException;

@Slf4j
@Component
public class WarehouseFeignClientFallbackFactory implements FallbackFactory<WarehouseClient> {

    @Override
    public WarehouseClient create(Throwable cause) {
        if (cause instanceof ProductInShoppingCartLowQuantityInWarehouse ||
                (cause instanceof FeignException && ((FeignException) cause).status() == 400)) {
            return new WarehouseClient() {
                @Override
                public void newProductInWarehouse(NewProductInWarehouseRequest request) {
                    throw (RuntimeException) cause; // Бросаем исходное исключение
                }

                @Override
                public BookedProductsDto checkProductQuantityEnoughForShoppingCart(ShoppingCartDto cartDto) {
                    throw new ProductInShoppingCartLowQuantityInWarehouse(cause.getMessage());
                }

                @Override
                public void addProductToWarehouse(AddProductToWarehouseRequest request) {
                    throw (RuntimeException) cause;
                }

                @Override
                public AddressDto getWarehouseAddress() {
                    throw (RuntimeException) cause;
                }
            };
        }

        // Если это реальный сбой (500, Network, Timeout)
        log.error("Склад недоступен. Причина: {}", cause.getMessage(), cause);
        return new WarehouseClient() {
            @Override
            public void newProductInWarehouse(NewProductInWarehouseRequest request) {
                throw new WarehouseServiceException("Сервис склада временно недоступен");
            }

            @Override
            public BookedProductsDto checkProductQuantityEnoughForShoppingCart(ShoppingCartDto cartDto) {
                throw new WarehouseServiceException("Сервис склада временно недоступен");
            }

            @Override
            public void addProductToWarehouse(AddProductToWarehouseRequest request) {
                throw new WarehouseServiceException("Сервис склада временно недоступен");
            }

            @Override
            public AddressDto getWarehouseAddress() {
                throw new WarehouseServiceException("Сервис склада временно недоступен");
            }
        };
    }
}
