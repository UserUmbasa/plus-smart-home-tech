package ru.yandex.practicum.telemetry.hub.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.telemetry.hub.model.device.DeviceAddedEvent;
import ru.yandex.practicum.telemetry.hub.model.device.DeviceRemovedEvent;
import ru.yandex.practicum.telemetry.hub.model.scenario.ScenarioAddedEvent;
import ru.yandex.practicum.telemetry.hub.model.scenario.ScenarioRemovedEvent;
import ru.yandex.practicum.telemetry.hub.type.HubEventType;

import java.time.Instant;
/**
 * Базовый абстрактный класс для событий хаба.
 * <p>
 * Данный класс является основой для всех типов событий в системе. Он содержит общую информацию,
 * которая присутствует во всех событиях хаба, и определяет базовый функционал.
 * <p>
 * Класс поддерживает полиморфную сериализацию/десериализацию JSON с автоматическим определением типа события
 * на основе поля "type".
 * <p>
 * Поддерживаемые типы событий:
 * <ul>
 *     <li>DEVICE_ADDED - событие добавления устройства</li>
 *     <li>DEVICE_REMOVED - событие удаления устройства</li>
 *     <li>SCENARIO_ADDED - событие добавления сценария</li>
 *     <li>SCENARIO_REMOVED - событие удаления сценария</li>
 * </ul>
 *
 * Основные компоненты класса:
 * <ul>
 *     <li>{@code hubId} - идентификатор хаба, к которому относится событие</li>
 *     <li>{@code timestamp} - временная метка события</li>
 *     <li>{@code type} - тип события (определяется в конкретных реализациях)</li>
 * </ul>
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        defaultImpl = HubEventType.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DeviceAddedEvent.class, name = "DEVICE_ADDED"),
        @JsonSubTypes.Type(value = DeviceRemovedEvent.class, name = "DEVICE_REMOVED"),
        @JsonSubTypes.Type(value = ScenarioAddedEvent.class, name = "SCENARIO_ADDED"),
        @JsonSubTypes.Type(value = ScenarioRemovedEvent.class, name = "SCENARIO_REMOVED ")
})
@Getter
@Setter
@ToString
public abstract class HubEvent {
    @NotBlank
    private String hubId;
    private Instant timestamp = Instant.now();

    @NotNull
    public abstract HubEventType getType();
}

