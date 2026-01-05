package ru.yandex.practicum.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Сущность складского товара.
 * <p>
 * Класс представляет собой модель товара, хранящегося на складе.
 * Отображается в таблице базы данных warehouse.products.
 * Содержит полную информацию о характеристиках товара и его количестве на складе.
 * <p>
 * Поля сущности:
 * <ul>
 *     <li>{@code productId} - уникальный идентификатор продукта</li>
 *     <li>{@code fragile} - флаг хрупкости товара</li>
 *     <li>{@code width} - ширина товара</li>
 *     <li>{@code height} - высота товара</li>
 *     <li>{@code depth} - глубина товара</li>
 *     <li>{@code weight} - вес товара</li>
 *     <li>{@code quantity} - количество товара на складе (по умолчанию 0)</li>
 * </ul>
 */
@Getter
@Setter
@Entity
@Table(name="warehouse.warehouse_products")
public class WarehouseProduct {
    @Id
    @Column(name = "product_id")
    private UUID productId;
    private Boolean fragile;
    private Double width;
    private Double height;
    private Double depth;
    private Double weight;
    private Integer quantity = 0;
}
