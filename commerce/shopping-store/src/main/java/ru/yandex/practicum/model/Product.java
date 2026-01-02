package ru.yandex.practicum.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "store.products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID productId;
    @Column(name = "name", nullable = false)
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
