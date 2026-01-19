package ru.yandex.practicum.dto.payment;

/**
 * Перечисление состояний платежа в системе.
 * <p>
 * Возможные состояния:
 * <ul>
 *     <li>{@code PENDING} - платёж находится в обработке, ожидает подтверждения</li>
 *     <li>{@code SUCCESS} - платёж успешно завершён и подтверждён</li>
 *     <li>{@code FAILED} - платёж не был выполнен, произошла ошибка</li>
 * </ul>
 */
public enum PaymentState {
    PENDING, SUCCESS, FAILED
}
