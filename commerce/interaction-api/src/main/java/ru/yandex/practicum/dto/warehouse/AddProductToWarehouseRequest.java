package ru.yandex.practicum.dto.warehouse;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

/**
 * DTO-объект представляет собой запрос на добавление определенного количества товара
 * на склад. Содержит минимально необходимую информацию для выполнения операции.
 * <p>
 * Основные характеристики:
 * <ul>
 *     <li>Содержит идентификатор товара и количество</li>
 * </ul>
 * Поля класса:
 * <ul>
 *     <li>{@code productId} - уникальный идентификатор продукта</li>
 *     <li>{@code quantity} - количество добавляемых единиц товара</li>
 * </ul>
 */
@Data
@AllArgsConstructor
public class AddProductToWarehouseRequest {
    @NotNull
    private UUID productId;
    @NotNull
    @Min(1)
    private Integer quantity;
}
