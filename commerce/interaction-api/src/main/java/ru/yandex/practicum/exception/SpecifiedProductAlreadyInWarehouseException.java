package ru.yandex.practicum.exception;

/**
 * Исключение, возникающее при попытке добавить товар, который уже присутствует на складе.
 * <p>
 * Данное исключение наследуется от RuntimeException и генерируется в случае,
 * когда система обнаруживает, что указанный товар уже зарегистрирован в складской системе.
 */
public class SpecifiedProductAlreadyInWarehouseException extends RuntimeException {
    public SpecifiedProductAlreadyInWarehouseException(String message) {
        super(message);
    }
}
