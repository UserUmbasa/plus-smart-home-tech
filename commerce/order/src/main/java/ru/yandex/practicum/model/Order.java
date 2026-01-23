package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.yandex.practicum.dto.order.OrderState;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Сущность заказа в системе электронной коммерции.
 * <p>
 * Класс представляет собой модель заказа пользователя.
 * Отображается в таблице базы данных orders.
 * Содержит полную информацию о составе заказа, его состоянии,
 * параметрах доставки и оплате.
 * <p>
 * Основные характеристики заказа включают список товаров,
 * их количество, общую стоимость, параметры доставки и статус.
 * <p>
 * Поля сущности:
 * <ul>
 *     <li>{@code orderId} - уникальный идентификатор заказа</li>
 *     <li>{@code orderState} - текущее состояние заказа (по умолчанию NEW)</li>
 *     <li>{@code products} - карта товаров заказа с указанием количества каждого</li>
 *     <li>{@code shoppingCartId} - идентификатор корзины, из которой сформирован заказ</li>
 *     <li>{@code username} - имя пользователя, сделавшего заказ</li>
 *     <li>{@code paymentId} - идентификатор платежа</li>
 *     <li>{@code deliveryId} - идентификатор доставки</li>
 *     <li>{@code deliveryWeight} - общий вес товаров в заказе</li>
 *     <li>{@code deliveryVolume} - общий объём товаров в заказе</li>
 *     <li>{@code fragile} - флаг хрупкости товаров в заказе</li>
 *     <li>{@code totalPrice} - итоговая стоимость заказа</li>
 *     <li>{@code deliveryPrice} - стоимость доставки</li>
 *     <li>{@code productPrice} - суммарная стоимость всех товаров</li>
 * </ul>
 */

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id //  идентификатор сущности
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id")
    private UUID orderId;
    // private UUID orderId = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");

    @Column(name = "order_state", nullable = false) // состояние заказа
    @Enumerated(EnumType.STRING)
    private OrderState orderState = OrderState.NEW;

    @ElementCollection // список товаров заказа с указанием количества каждого
    @CollectionTable(name = "order_products", joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<UUID, Integer> products;

    @Column(name = "shopping_cart_id") // ссылка на продуктовую корзину, из которой был собран заказ
    private UUID shoppingCartId;

    private String username; //  поиск по имени пользователя

    @Column(name = "payment_id") // ссылка на оплату
    private UUID paymentId;

    @Column(name = "delivery_id") //ссылка на доставку
    private UUID deliveryId;

    @Column(name = "delivery_weight")  // вес товаров
    private Double deliveryWeight;

    @Column(name = "delivery_volume") // объём товаров
    private Double deliveryVolume;

    private Boolean fragile; // признак хрупкости

    @Column(name = "total_price") // итоговая цена
    private BigDecimal totalPrice;

    @Column(name = "delivery_price") // цена доставки
    private BigDecimal deliveryPrice;

    @Column(name = "product_price") // цена всех товаров
    private BigDecimal productPrice;
}