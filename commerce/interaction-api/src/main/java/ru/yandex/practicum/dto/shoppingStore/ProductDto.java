package ru.yandex.practicum.dto.shoppingStore;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

/**
 DTO-объект предназначен для передачи данных о товаре между слоями приложения.
 * Содержит основные характеристики продукта, включая состояние, категорию и цену.
 * <p>
 * Поля класса:
 * <ul>
 *     <li>{@code productId} - уникальный идентификатор продукта</li>
 *     <li>{@code productName} - название продукта</li>
 *     <li>{@code description} - описание товара</li>
 *     <li>{@code imageSrc} - ссылка на изображение товара</li>
 *     <li>{@code quantityState} - состояние количества товара</li>
 *     <li>{@code productState} - общее состояние продукта</li>
 *     <li>{@code productCategory} - категория товара</li>
 *     <li>{@code price} - цена продукта</li>
 * </ul>
 */
@Builder
@Data
public class ProductDto {
    private UUID productId;
    @NotNull
    private String productName;
    @NotNull
    private String description;
    private String imageSrc;
    @NotNull
    private QuantityState quantityState;
    @NotNull
    private ProductState productState;
    private ProductCategory productCategory;
    @NotNull
    @Min(1)
    private BigDecimal price;
}
