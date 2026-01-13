package ru.yandex.practicum.exception;

/**
 * Исключение, возникающее при попытке получить информацию о товаре,
 * который не найден в системе.
 */

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
