package ru.yandex.practicum.dto.warehouse;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

/**
 * DTO-объект запроса на сборку товаров для заказа.
 * <p>
 * Данный объект применяется при процессе комплектации заказа на складе и содержит
 * информацию о необходимых товарах и их количестве.
 * <p>
 * Поля DTO:
 * <ul>
 *     <li>{@code products} - обязательный список товаров с указанием количества каждого
 *     товара, который необходимо собрать для заказа</li>
 *     <li>{@code orderId} - обязательный уникальный идентификатор заказа,
 *     к которому относятся собираемые товары</li>
 * </ul>
 */
@Data
@AllArgsConstructor
public class AssemblyProductsForOrderRequest {
    /**
     * Карта товаров с указанием количества для сборки
     * {@code @NotNull} - поле обязательно должно быть заполнено
     * <p>
     * Ключом является уникальный идентификатор товара, значением - необходимое количество
     */
    @NotNull
    private Map<UUID, Integer> products;

    /**
     * Уникальный идентификатор заказа
     * {@code @NotNull} - поле обязательно должно быть заполнено
     */
    @NotNull
    private UUID orderId;
}
