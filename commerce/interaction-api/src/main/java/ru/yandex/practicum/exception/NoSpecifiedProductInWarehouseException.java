package ru.yandex.practicum.exception;

/**
 * Исключение, возникающее при попытке выполнить операцию с товаром,
 * которого нет на складе.
 */
public class NoSpecifiedProductInWarehouseException extends RuntimeException {
    public NoSpecifiedProductInWarehouseException(String message) {
        super(message);
    }
}
