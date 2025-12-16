package ru.yandex.practicum.telemetry.hub.type;

/**
 * Перечисление типов событий хаба.
 * <p>
 * Данный enum определяет все возможные типы событий, которые могут происходить в системе хаба.
 * Каждое событие соответствует определенному действию в системе и используется для идентификации
 * типа происходящих изменений.
 * <p>
 * Доступные типы событий:
 * <ul>
 *     <li>{@code DEVICE_ADDED} - событие добавления нового устройства в систему</li>
 *     <li>{@code DEVICE_REMOVED} - событие удаления устройства из системы</li>
 *     <li>{@code SCENARIO_ADDED} - событие создания нового сценария</li>
 *     <li>{@code SCENARIO_REMOVED} - событие удаления существующего сценария</li>
 * </ul>
 */
public enum HubEventType {
    DEVICE_ADDED, DEVICE_REMOVED, SCENARIO_ADDED, SCENARIO_REMOVED;
}
