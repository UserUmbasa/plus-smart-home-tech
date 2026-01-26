package ru.yandex.practicum.dto.delivery;

/**
 * Перечисление состояний доставки в системе.
 * <p>
 * Порядок состояний отражает логическую последовательность процесса доставки:
 * от создания до завершения или отмены.
 * <p>
 * Возможные состояния:
 * <ul>
 *     <li>{@code CREATED} - доставка создана, но еще не начата</li>
 *     <li>{@code IN_PROGRESS} - процесс доставки находится в активной фазе</li>
 *     <li>{@code DELIVERED} - доставка успешно завершена, заказ получен клиентом</li>
 *     <li>{@code FAILED} - произошла ошибка при доставке</li>
 *     <li>{@code CANCELLED} - доставка отменена</li>
 * </ul>
 */
public enum DeliveryState {
    CREATED, IN_PROGRESS, DELIVERED, FAILED, CANCELLED
}
