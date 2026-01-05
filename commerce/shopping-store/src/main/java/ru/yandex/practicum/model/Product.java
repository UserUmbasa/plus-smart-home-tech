package ru.yandex.practicum.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.dto.shoppingStore.ProductCategory;
import ru.yandex.practicum.dto.shoppingStore.ProductState;
import ru.yandex.practicum.dto.shoppingStore.QuantityState;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Сущность продукта магазина.
 * <p>
 * Класс представляет собой модель товара, хранящегося в магазине.
 * Отображается в таблице базы данных store.products.
 * Содержит полную информацию о характеристиках товара, его состоянии и категории.
 *
 * Основные характеристики:
 * <ul>
 *     <li>Хранится в таблице store.products</li>
 *     <li>Содержит основные характеристики товара</li>
 *     <li>Отслеживает состояние товара и его категорию</li>
 *     <li>Содержит информацию о цене и доступности</li>
 * </ul>
 *
 * Поля сущности:
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
@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID productId;
    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column(nullable = false)
    private String description;
    private String imageSrc;
    @Enumerated(EnumType.STRING)
    @Column(name = "quantity_state", nullable = false)
    private QuantityState quantityState;
    @Column(name = "product_state", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductState productState;
    @Enumerated(EnumType.STRING)
    @Column(name = "product_category")
    private ProductCategory productCategory;
    @Column(nullable = false)
    private BigDecimal price;
}
