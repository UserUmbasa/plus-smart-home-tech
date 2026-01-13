package ru.yandex.practicum.exception;

public class InactiveShoppingCartException extends RuntimeException {
    public InactiveShoppingCartException(String message) {
        super(message);
    }
}