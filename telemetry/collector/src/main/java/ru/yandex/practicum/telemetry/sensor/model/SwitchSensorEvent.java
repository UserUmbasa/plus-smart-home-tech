package ru.yandex.practicum.telemetry.sensor.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.telemetry.sensor.type.SensorEventType;

/**
 * Событие датчика переключения.
 * <p>
 * Данный класс представляет собой событие, генерируемое датчиком переключения.
 * Он наследуется от базового класса SensorEvent и содержит информацию о состоянии переключателя.
 *
 * Основные характеристики:
 * <ul>
 *     <li>Наследуется от {@code SensorEvent}</li>
 *     <li>Содержит данные о текущем состоянии переключателя</li>
 * </ul>
 *
 * Поле класса:
 * <ul>
 *     <li>{@code state} - текущее состояние переключателя (логическое значение)</li>
 * </ul>
 */
@Getter
@Setter
@ToString(callSuper = true)
public class SwitchSensorEvent extends SensorEvent {
    @NotNull
    private Boolean state;

    @Override
    public SensorEventType getType() {
        return SensorEventType.SWITCH_SENSOR_EVENT;
    }
}
