package ru.yandex.practicum.telemetry.sensor.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.telemetry.sensor.type.SensorEventType;


/**
 * Событие датчика освещенности.
 * <p>
 * Данный класс представляет собой событие, генерируемое датчиком освещенности.
 * Он наследуется от базового класса SensorEvent и содержит специфичные для датчика освещенности параметры.
 *
 * Основные характеристики:
 * <ul>
 *     <li>Наследуется от {@code SensorEvent}</li>
 *     <li>Содержит данные о качестве связи и уровне освещенности</li>
 *     <li>Автоматически реализует геттеры, сеттеры и метод toString</li>
 * </ul>
 *
 * Поля класса:
 * <ul>
 *     <li>{@code linkQuality} - качество связи с датчиком (целочисленное значение)</li>
 *     <li>{@code luminosity} - уровень освещенности (целочисленное значение)</li>
 * </ul>
 */
@Getter
@Setter
@ToString(callSuper = true)
public class LightSensorEvent extends SensorEvent {
    @NotNull
    private int linkQuality;
    @NotNull
    private int luminosity;

    @Override
    public SensorEventType getType() {
        return SensorEventType.LIGHT_SENSOR_EVENT;
    }
}
