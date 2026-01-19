package ru.yandex.practicum.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

/**
 * DTO-объект, представляющий информацию о заказе.
 * <p>
 * Поля DTO:
 * <ul>
 *     <li>{@code orderId} - обязательный уникальный идентификатор заказа</li>
 *     <li>{@code shoppingCartId} - идентификатор корзины, из которой сформирован заказ</li>
 *     <li>{@code products} - обязательный список товаров заказа с указанием количества</li>
 *     <li>{@code paymentId} - идентификатор платёжной операции</li>
 *     <li>{@code deliveryId} - идентификатор доставки</li>
 *     <li>{@code orderState} - текущее состояние заказа</li>
 *     <li>{@code deliveryWeight} - общий вес товаров в заказе</li>
 *     <li>{@code deliveryVolume} - общий объём товаров в заказе</li>
 *     <li>{@code fragile} - флаг хрупкости товаров</li>
 *     <li>{@code totalPrice} - итоговая стоимость заказа</li>
 *     <li>{@code deliveryPrice} - стоимость доставки</li>
 *     <li>{@code productPrice} - суммарная стоимость всех товаров</li>
 * </ul>
 */
@Data
public class OrderDto {
    /**
     * Уникальный идентификатор заказа
     * {@code @NotNull} - поле обязательно должно быть заполнено
     */
    @NotNull
    private UUID orderId;

    /**
     * Идентификатор корзины, из которой сформирован заказ
     */
    private UUID shoppingCartId;

    /**
     * Список товаров заказа с указанием количества
     * {@code @NotNull} - поле обязательно должно быть заполнено
     * <p>
     * Ключом является идентификатор товара, значением - количество единиц товара
     */
    @NotNull
    private Map<UUID, Integer> products;

    /**
     * Идентификатор платёжной операции
     */
    private UUID paymentId;

    /**
     * Идентификатор доставки
     */
    private UUID deliveryId;

    /**
     * Текущее состояние заказа
     */
    private OrderState orderState;

    /**
     * Общий вес товаров в заказе
     */
    private Double deliveryWeight;

    /**
     * Общий объём товаров в заказе
     */
    private Double deliveryVolume;

    /**
     * Флаг хрупкости товаров
     * Указывает на наличие хрупких товаров в заказе
     */
    private Boolean fragile;

    /**
     * Итоговая стоимость заказа
     */
    private BigDecimal totalPrice;

    /**
     * Стоимость доставки
     */
    private BigDecimal deliveryPrice;

    /**
     * Суммарная стоимость всех товаров
     */
    private BigDecimal productPrice;
}
