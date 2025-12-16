package ru.yandex.practicum.telemetry.sensor.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.telemetry.sensor.type.SensorEventType;

/**
 * Событие датчика температуры.
 * <p>
 * Данный класс представляет собой событие, генерируемое датчиком температуры.
 * Он наследуется от базового класса SensorEvent и содержит измерения температуры в двух единицах измерения.
 *
 * Основные характеристики:
 * <ul>
 *     <li>Наследуется от {@code SensorEvent}</li>
 *     <li>Содержит данные о температуре в градусах Цельсия и Фаренгейта</li>
 *     <li>Обеспечивает хранение температурных показаний в двух форматах</li>
 * </ul>
 *
 * Поля класса:
 * <ul>
 *     <li>{@code temperatureC} - температура в градусах Цельсия (целочисленное значение)</li>
 *     <li>{@code temperatureF} - температура в градусах Фаренгейта (целочисленное значение)</li>
 * </ul>
 */
@Getter
@Setter
@ToString(callSuper = true)
public class TemperatureSensorEvent extends SensorEvent {
    @NotNull
    private int temperatureC;
    @NotNull
    private int temperatureF;

    @Override
    public SensorEventType getType() {
        return SensorEventType.TEMPERATURE_SENSOR_EVENT;
    }
}
