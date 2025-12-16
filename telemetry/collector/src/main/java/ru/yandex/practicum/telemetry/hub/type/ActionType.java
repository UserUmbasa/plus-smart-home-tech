package ru.yandex.practicum.telemetry.hub.type;

/**
 * Перечисление типов действий, доступных в системе.
 * <p>
 * Данный enum определяет все возможные типы действий, которые могут быть выполнены
 * над устройствами или другими элементами системы.
 * <p>
 * Доступные типы действий:
 * <ul>
 *     <li>{@code ACTIVATE} - активация элемента</li>
 *     <li>{@code DEACTIVATE} - деактивация элемента</li>
 *     <li>{@code INVERSE} - инверсия состояния элемента</li>
 *     <li>{@code SET_VALUE} - установка значения элемента</li>
 * </ul>
 */
public enum ActionType {
    ACTIVATE, DEACTIVATE, INVERSE, SET_VALUE;
}
