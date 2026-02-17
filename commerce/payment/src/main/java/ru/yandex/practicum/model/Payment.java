package ru.yandex.practicum.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.yandex.practicum.dto.payment.PaymentState;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Сущность платёжной операции в системе.
 * <p>
 * Поля сущности:
 * <ul>
 *     <li>{@code paymentId} - уникальный идентификатор платёжной операции</li>
 *     <li>{@code totalPayment} - общая сумма платежа</li>
 *     <li>{@code deliveryTotal} - сумма за доставку</li>
 *     <li>{@code feeTotal} - сумма дополнительных сборов</li>
 *     <li>{@code productTotal} - сумма за товары</li>
 *     <li>{@code paymentState} - текущее состояние платежа (по умолчанию PENDING)</li>
 *     <li>{@code orderId} - идентификатор связанного заказа</li>
 * </ul>
 */
@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name = "payments")
public class Payment {
    /**
     * Уникальный идентификатор платёжной операции
     * Генерируется автоматически при создании записи
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "payment_id")
    private UUID paymentId;

    /**
     * Общая сумма платежа
     * Включает все компоненты оплаты
     */
    @Column(name = "total_payment")
    private BigDecimal totalPayment;

    /**
     * Сумма за доставку
     * Стоимость услуг доставки
     */
    @Column(name = "delivery_total")
    private BigDecimal deliveryTotal;

    /**
     * Сумма дополнительных сборов
     * Включает комиссии и другие дополнительные платежи
     */
    @Column(name = "fee_total")
    private BigDecimal feeTotal;

    /**
     * Сумма за товары
     * Стоимость всех товаров в заказе
     */
    @Column(name = "product_total")
    private BigDecimal productTotal;

    /**
     * Текущее состояние платежа
     * {@code @Enumerated(EnumType.STRING)} - сохраняется в виде строки
     * По умолчанию установлено значение PENDING
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_state")
    private PaymentState paymentState = PaymentState.PENDING;

    /**
     * Идентификатор связанного заказа
     * Связь с заказом, к которому относится платёж
     */
    @Column(name = "order_id")
    private UUID orderId;
}
