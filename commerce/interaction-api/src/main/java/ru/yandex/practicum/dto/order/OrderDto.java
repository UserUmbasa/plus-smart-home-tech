package ru.yandex.practicum.dto.order;

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
 *     <li>{@code state} - текущее состояние заказа</li>
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
    private UUID orderId;
    private UUID shoppingCartId;
    private Map<UUID, Integer> products;
    private UUID paymentId;
    private UUID deliveryId;
    private OrderState state;
    private Double deliveryWeight;
    private Double deliveryVolume;
    private Boolean fragile;
    private BigDecimal totalPrice;
    private BigDecimal deliveryPrice;
    private BigDecimal productPrice;
    private String username;
}
