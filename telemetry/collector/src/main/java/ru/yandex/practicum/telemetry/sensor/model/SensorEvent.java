package ru.yandex.practicum.telemetry.sensor.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.telemetry.sensor.type.SensorEventType;

import java.time.Instant;

/**
 * Абстрактный класс событий датчиков, предназначенный для сериализации/десериализации различных типов событий.
 * <p>
 * Данный класс является базовым для всех типов событий датчиков и обеспечивает полиморфную сериализацию
 * с использованием аннотаций Jackson. Каждый подтип события соответствует определенному типу датчика.
 * <p>
 * Основные характеристики:
 * <ul>
 *     <li>Использует полиморфную сериализацию на основе свойства "type"</li>
 *     <li>Поддерживает различные типы датчиков через подклассы</li>
 *     <li>Содержит базовую информацию о событии</li>
 * </ul>
 * <p>
 * Поддерживаемые типы событий:
 * <blockquote><pre>
 *     LightSensorEvent - событие от датчика освещенности
 *     ClimateSensorEvent - событие от климатического датчика
 *     MotionSensorEvent - событие от датчика движения
 *     TemperatureSensorEvent - событие от температурного датчика
 *     SwitchSensorEvent - событие от датчика переключения
 * </pre></blockquote>
 * <p>
 * Обязательные поля для всех событий:
 * <ul>
 *     <li>id - уникальный идентификатор события</li>
 *     <li>hubId - идентификатор хаба, с которого пришло событие</li>
 *     <li>timestamp - временная метка события</li>
 *     <li>type - тип события датчика</li>
 * </ul>
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        defaultImpl = SensorEventType.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = LightSensorEvent.class, name = "LIGHT_SENSOR_EVENT"),
        @JsonSubTypes.Type(value = ClimateSensorEvent.class, name = "CLIMATE_SENSOR_EVENT"),
        @JsonSubTypes.Type(value = MotionSensorEvent.class, name = "MOTION_SENSOR_EVENT"),
        @JsonSubTypes.Type(value = TemperatureSensorEvent.class, name = "TEMPERATURE_SENSOR_EVENT"),
        @JsonSubTypes.Type(value = SwitchSensorEvent.class, name = "SWITCH_SENSOR_EVENT")
})
@Getter
@Setter
@ToString
public abstract class SensorEvent {
    @NotBlank
    private String id;
    @NotBlank
    private String hubId;
    private Instant timestamp = Instant.now();

    @NotNull
    public abstract SensorEventType getType();
}