package ru.yandex.practicum.dto.warehouse;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO-объект предназначен для хранения и передачи информации о трехмерных размерах объекта.
 * Все параметры являются обязательными и должны содержать положительные значения.
 *
 * Поля класса:
 * <ul>
 *     <li>{@code width} - ширина объекта в единицах измерения</li>
 *     <li>{@code height} - высота объекта в единицах измерения</li>
 *     <li>{@code depth} - глубина объекта в единицах измерения</li>
 * </ul>
 */
@Data
public class DimensionDto {
    @NotNull
    @Min(1)
    private Double width;
    @NotNull
    @Min(1)
    private Double height;
    @NotNull
    @Min(1)
    private Double depth;
}
