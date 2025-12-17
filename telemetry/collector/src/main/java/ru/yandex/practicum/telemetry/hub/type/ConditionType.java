package ru.yandex.practicum.telemetry.hub.type;

/**
 * Перечисление типов условий для мониторинга состояния системы.
 * <p>
 * Данный enum определяет все возможные типы условий, которые могут быть использованы
 * для отслеживания состояния различных датчиков и устройств в системе.
 * <p>
 * Доступные типы условий:
 * <ul>
 *     <li>{@code MOTION} - условие по движению</li>
 *     <li>{@code LUMINOSITY} - условие по уровню освещенности</li>
 *     <li>{@code SWITCH} - условие по состоянию переключателя</li>
 *     <li>{@code TEMPERATURE} - условие по температуре</li>
 *     <li>{@code CO2LEVEL} - условие по уровню CO2</li>
 *     <li>{@code HUMIDITY} - условие по уровню влажности</li>
 * </ul>
 */
public enum ConditionType {
    MOTION, LUMINOSITY, SWITCH, TEMPERATURE, CO2LEVEL, HUMIDITY;
}
