package ru.yandex.practicum.dto.shoppingStore;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * DTO-объект используется для передачи постраничного списка товаров,
 * включая информацию о сортировке и содержимом страницы.
 * <p>
 * Составные части:
 * <ul>
 *     <li>Контент страницы с товарами</li>
 *     <li>Настройки сортировки</li>
 * </ul>
 *
 * Поля класса:
 * <ul>
 *     <li>{@code content} - список товаров на странице</li>
 *     <li>{@code sort} - информация о сортировке</li>
 * </ul>
 */
@Data
@Builder
public class ProductPageDto {
    List<ProductDto> content;
    Sort sort;

    public ProductPageDto(List<ProductDto> content, Sort sort) {
        this.content = content;
        this.sort = sort;
    }
}
