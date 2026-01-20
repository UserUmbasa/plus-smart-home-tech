package ru.yandex.practicum.dto.payment;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO-объект, представляющий информацию о платеже.
 * Поля DTO:
 * <ul>
 *     <li>{@code paymentId} - уникальный идентификатор платёжной операции</li>
 *     <li>{@code totalPayment} - общая сумма платежа</li>
 *     <li>{@code deliveryTotal} - сумма за доставку</li>
 *     <li>{@code feeTotal} - сумма дополнительных сборов</li>
 * </ul>
 */
@Data
public class PaymentDto {
    /**
     * Уникальный идентификатор платёжной операции
     */
    private UUID paymentId;

    /**
     * Общая сумма платежа
     * Включает в себя все компоненты оплаты
     */
    private BigDecimal totalPayment;

    /**
     * Сумма за доставку
     * Стоимость услуг доставки
     */
    private BigDecimal deliveryTotal;

    /**
     * Сумма дополнительных сборов
     * Включает комиссии и другие дополнительные платежи
     */
    private BigDecimal feeTotal;
}
