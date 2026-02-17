package ru.yandex.practicum.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

/**
 * DTO-объект запроса на возврат товаров по заказу.
 * <p>
 * Поля DTO:
 * <ul>
 *     <li>{@code orderId} - обязательный уникальный идентификатор заказа,
 *     по которому производится возврат товаров</li>
 *     <li>{@code products} - обязательный список товаров для возврата с указанием
 *     количества каждого товара</li>
 * </ul>
 */
@Data
public class ProductReturnRequest {
    /**
     * Уникальный идентификатор заказа
     * {@code @NotNull} - поле обязательно должно быть заполнено
     * <p>
     * Идентификатор заказа, к которому относятся возвращаемые товары
     */
    @NotNull
    private UUID orderId;

    /**
     * Список товаров для возврата
     * {@code @NotNull} - поле обязательно должно быть заполнено
     * <p>
     * Карта, где ключом является уникальный идентификатор товара,
     * а значением - количество единиц товара, подлежащих возврату
     */
    @NotNull
    private Map<UUID, Integer> products;
}

