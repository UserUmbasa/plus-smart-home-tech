package ru.yandex.practicum.exception;

import lombok.Data;

@Data
public class ErrorResponseDto {
    private int status;
    private String code; // Уникальный код ошибки
    private String message;
    private long timestamp;
    public ErrorResponseDto(int status, String code, String message, long timestamp) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.timestamp = timestamp;
    }
}
