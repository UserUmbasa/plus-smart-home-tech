package ru.yandex.practicum.dto.warehouse;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO-объект содержит агрегированную информацию о забронированных товарах,
 * включая их общий вес, объем и наличие хрупких предметов.
 * <p>
 * Поля класса:
 * <ul>
 *     <li>{@code deliveryWeight} - общий вес забронированных товаров</li>
 *     <li>{@code deliveryVolume} - общий объем забронированных товаров</li>
 *     <li>{@code fragile} - наличие хрупких товаров в бронировании</li>
 * </ul>
 */
@Data
public class BookedProductsDto {
    @NotNull
    private Double deliveryWeight = 0.0;
    @NotNull
    private Double deliveryVolume = 0.0;
    @NotNull
    private Boolean fragile = false;
}
