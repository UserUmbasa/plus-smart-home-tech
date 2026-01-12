package ru.yandex.practicum.exception;

public class WarehouseServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public WarehouseServiceException(String message) {
        super(message);
    }

    public WarehouseServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

