package ru.yandex.practicum.hubrouter;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class TemperatureSensor {
    private String id;
    private int temperatureC;
    private int temperatureF;
}
