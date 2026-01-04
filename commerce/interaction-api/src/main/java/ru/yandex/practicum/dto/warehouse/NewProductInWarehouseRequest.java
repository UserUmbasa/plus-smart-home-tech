package ru.yandex.practicum.dto.warehouse;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

/**
 * DTO-объект представляет собой запрос на регистрацию нового продукта в системе складского учета.
 * Содержит основную информацию о товаре, необходимую для его корректного размещения на складе.
 * <ul></ul>
 * Поля класса:
 * <ul>
 *     <li>{@code productId} - уникальный идентификатор продукта</li>
 *     <li>{@code fragile} - флаг хрупкости товара (true/false)</li>
 *     <li>{@code dimension} - габариты товара в формате DTO</li>
 *     <li>{@code weight} - вес товара (положительное число, минимум 1)</li>
 * </ul>
 */
@Data
public class NewProductInWarehouseRequest {
    @NotNull
    private UUID productId;
    private Boolean fragile;
    @NotNull
    private DimensionDto dimension;
    @NotNull
    @Min(1)
    private Double weight;
}
