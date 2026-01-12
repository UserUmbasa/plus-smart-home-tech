package ru.yandex.practicum.dto.shoppingCart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

/**
 * DTO-объект представляет собой запрос на изменение количества определенного товара
 * в системе. Используется для обновления информации о наличии товара.
 * <p>
 * Поля класса:
 * <ul>
 *     <li>{@code productId} - уникальный идентификатор продукта</li>
 *     <li>{@code newQuantity} - новое количество товара</li>
 * </ul>
 */
@Data
public class ChangeProductQuantityRequest {
    @NotNull
    private UUID productId;
    @NotNull
    @Min(1)
    private Integer newQuantity;
}
