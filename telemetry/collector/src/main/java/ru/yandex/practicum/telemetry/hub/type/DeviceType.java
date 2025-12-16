package ru.yandex.practicum.telemetry.hub.type;

/**
 * Перечисление типов устройств, используемых в системе мониторинга.
 * <p>
 * Данный enum определяет все возможные типы датчиков и устройств,
 * которые могут генерировать события в системе.
 * <p>
 * Доступные типы устройств:
 * <ul>
 *     <li>MOTION_SENSOR - датчик движения</li>
 *     <li>TEMPERATURE_SENSOR - температурный датчик</li>
 *     <li>LIGHT_SENSOR - датчик освещенности</li>
 *     <li>CLIMATE_SENSOR - климатический датчик</li>
 *     <li>SWITCH_SENSOR - датчик переключения</li>
 * </ul>
 */
public enum DeviceType {
    MOTION_SENSOR, TEMPERATURE_SENSOR, LIGHT_SENSOR, CLIMATE_SENSOR, SWITCH_SENSOR;
}
