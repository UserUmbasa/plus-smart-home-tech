package ru.yandex.practicum.telemetry.sensor.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.telemetry.sensor.type.SensorEventType;

/**
 * Класс, представляющий событие климатического датчика.
 * <p>
 * Данный класс является реализацией конкретного типа события датчика,
 * который содержит информацию о климатических показателях: температуре,
 * влажности и уровне CO2.
 * <p>
 * Наследует базовый класс SensorEvent и реализует специфичную
 * функциональность для климатического датчика.
 */
@Getter
@Setter
@ToString(callSuper = true)
public class ClimateSensorEvent extends SensorEvent {
    @NotNull
    private int temperatureC;
    @NotNull
    private int humidity;
    @NotNull
    private int co2Level;

    @Override
    public SensorEventType getType() {
        return SensorEventType.CLIMATE_SENSOR_EVENT;
    }
}
