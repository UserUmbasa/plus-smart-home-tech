package ru.yandex.practicum.telemetry.sensor.type;

/**
 * Перечисление типов событий датчиков, используемых в системе мониторинга и управления.
 * <p>
 * Данный enum определяет основные типы событий, которые могут генерироваться различными типами датчиков.
 * <p>
 * Доступные типы событий датчиков:
 * <blockquote><pre>
 *     MOTION_SENSOR_EVENT - событие от датчика движения
 *     TEMPERATURE_SENSOR_EVENT - событие от температурного датчика
 *     LIGHT_SENSOR_EVENT - событие от датчика освещенности
 *     CLIMATE_SENSOR_EVENT - событие от климатического датчика
 *     SWITCH_SENSOR_EVENT - событие от датчика переключения
 * </pre></blockquote>
 */
public enum SensorEventType {
    MOTION_SENSOR_EVENT, TEMPERATURE_SENSOR_EVENT, LIGHT_SENSOR_EVENT, CLIMATE_SENSOR_EVENT, SWITCH_SENSOR_EVENT;
}
