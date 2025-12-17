package ru.yandex.practicum.telemetry.sensor.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.telemetry.sensor.type.SensorEventType;

/**
 * Событие датчика движения.
 * <p>
 * Данный класс представляет собой событие, генерируемое датчиком движения.
 * Он наследуется от базового класса SensorEvent и содержит специфичные для датчика движения параметры.
 *
 * Основные характеристики:
 * <ul>
 *     <li>Наследуется от {@code SensorEvent}</li>
 *     <li>Содержит данные о качестве связи, обнаружении движения и напряжении</li>
 *     <li>Автоматически реализует геттеры, сеттеры и метод toString</li>
 * </ul>
 *
 * Поля класса:
 * <ul>
 *     <li>{@code linkQuality} - качество связи с датчиком (целочисленное значение)</li>
 *     <li>{@code motion} - наличие движения (логическое значение)</li>
 *     <li>{@code voltage} - напряжение датчика (целочисленное значение)</li>
 * </ul>
 */
@Getter
@Setter
@ToString(callSuper = true)
public class MotionSensorEvent extends SensorEvent {
    @NotNull
    private int linkQuality;
    @NotNull
    private Boolean motion;
    @NotNull
    private int voltage;

    @Override
    public SensorEventType getType() {
        return SensorEventType.MOTION_SENSOR_EVENT;
    }
}
