package ru.yandex.practicum.dto.order;

/**
 * Перечисление состояний заказа в системе.
 * <p>
 * Порядок состояний отражает логическую последовательность обработки заказа:
 * от создания до завершения или отмены.
 * <p>
 * Возможные состояния:
 * <ul>
 *     <li>{@code NEW} - новый заказ, только что созданный</li>
 *     <li>{@code ON_PAYMENT} - заказ находится в процессе оплаты</li>
 *     <li>{@code ON_DELIVERY} - заказ находится в процессе доставки</li>
 *     <li>{@code DONE} - заказ собран и готов к доставке</li>
 *     <li>{@code DELIVERED} - заказ доставлен клиенту</li>
 *     <li>{@code ASSEMBLED} - заказ находится в процессе сборки</li>
 *     <li>{@code PAID} - заказ оплачен</li>
 *     <li>{@code COMPLETED} - заказ полностью выполнен</li>
 *     <li>{@code DELIVERY_FAILED} - произошла ошибка при доставке</li>
 *     <li>{@code ASSEMBLY_FAILED} - произошла ошибка при сборке</li>
 *     <li>{@code PAYMENT_FAILED} - произошла ошибка при оплате</li>
 *     <li>{@code PRODUCT_RETURNED} - товар возвращен</li>
 *     <li>{@code CANCELED} - заказ отменен</li>
 * </ul>
 */
public enum OrderState {
    NEW,
    ON_PAYMENT,
    ON_DELIVERY,
    DONE,
    DELIVERED,
    ASSEMBLED,
    PAID,
    COMPLETED,
    DELIVERY_FAILED,
    ASSEMBLY_FAILED,
    PAYMENT_FAILED,
    PRODUCT_RETURNED,
    CANCELED
}
