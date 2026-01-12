package ru.yandex.practicum.api.warehouse;

import feign.Response;
import feign.codec.ErrorDecoder;
import ru.yandex.practicum.exception.ProductInShoppingCartLowQuantityInWarehouse;


public class CustomFeignErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        // Проверяем, если статус 400 (Bad Request)
        if (response.status() == 400) {

            return new ProductInShoppingCartLowQuantityInWarehouse("Недостаточно товара на складе (из Feign)");
        }

        // Для всех остальных ошибок (500, 404 и т.д.)
        return defaultDecoder.decode(methodKey, response);
    }
}
