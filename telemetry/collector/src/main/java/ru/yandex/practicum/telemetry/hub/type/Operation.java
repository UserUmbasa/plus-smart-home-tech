package ru.yandex.practicum.telemetry.hub.type;

/**
 * Перечисление типов операций сравнения.
 * <p>
 * Данный enum определяет основные операции сравнения, которые могут использоваться
 * при проверке условий в системе.
 * <p>
 * Доступные операции:
 * <ul>
 *     <li>{@code EQUALS} - операция равенства (равно)</li>
 *     <li>{@code GREATER_THAN} - операция больше (больше чем)</li>
 *     <li>{@code LOWER_THAN} - операция меньше (меньше чем)</li>
 * </ul>
 */
public enum Operation {
    EQUALS, GREATER_THAN, LOWER_THAN;
}
