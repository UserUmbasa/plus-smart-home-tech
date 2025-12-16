package ru.yandex.practicum.telemetry.hub.model.scenario;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.telemetry.hub.type.ActionType;

/**
 * Класс, представляющий действие над устройством.
 * <p>
 * Данный класс используется для описания действий, которые необходимо выполнить
 * над конкретным датчиком или устройством в системе.
 *
 * Основные поля класса:
 * <ul>
 *     <li>{@code sensorId} - уникальный идентификатор датчика/устройства.
 *         Поле обязательно для заполнения и должно содержать валидный идентификатор
 *         существующего устройства в системе.</li>
 *
 *     <li>{@code type} - тип действия, которое необходимо выполнить.
 *         Определяет конкретное действие над устройством из перечня доступных типов действий.
 *         {@see ActionType}</li>
 *
 *     <li>{@code value} - значение параметра для действия.
 *         Используется в зависимости от типа действия:
 *         <ul>
 *             <li>Для SET_VALUE действия содержит устанавливаемое значение</li>
 *             <li>Для температурных датчиков может содержать целевую температуру</li>
 *             <li>Для других типов действий может быть не использовано</li>
 *         </ul>
 *     </li>
 * </ul>
 */

@Getter
@Setter
public class DeviceAction {
    @NotBlank
    private String sensorId;
    private ActionType type;
    private int value;
}
