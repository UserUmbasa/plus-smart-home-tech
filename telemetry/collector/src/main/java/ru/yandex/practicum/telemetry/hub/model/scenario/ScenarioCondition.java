package ru.yandex.practicum.telemetry.hub.model.scenario;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.telemetry.hub.type.Operation;
import ru.yandex.practicum.telemetry.hub.type.ConditionType;

/**
 * Класс, представляющий условие сценария.
 * <p>
 * Данный класс используется для описания условий, при которых активируется сценарий.
 * Условие состоит из идентификатора датчика, типа условия, операции сравнения
 * и значения для проверки.
 * <p>
 * Структура условия включает следующие компоненты:
 * <ul>
 *     <li>Идентификатор датчика (sensorId) - уникальный идентификатор устройства</li>
 *     <li>Тип условия (type) - определяет тип проверяемого параметра</li>
 *     <li>Операция сравнения (operation) - определяет способ проверки условия</li>
 *     <li>Значение (value) - числовое значение для сравнения</li>
 * </ul>
 */
@Getter
@Setter
public class ScenarioCondition {
    @NotBlank
    private String sensorId;
    private ConditionType type;
    private Operation operation;
    private int value;
}
