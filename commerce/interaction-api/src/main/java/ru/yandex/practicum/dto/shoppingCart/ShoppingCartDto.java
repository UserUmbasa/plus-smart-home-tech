package ru.yandex.practicum.dto.shoppingCart;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

/**
 * DTO-объект представляет собой модель корзины покупок, содержащей список товаров
 * с указанием их количества. Используется для передачи данных между слоями приложения.
 * <p>
 * Основные характеристики:
 * <ul>
 *     <li>Содержит уникальный идентификатор корзины</li>
 *     <li>Хранит список товаров в виде карты</li>
 * </ul>
 *
 * Структура данных:
 * <ul>
 *     <li>Каждый товар представлен в виде пары ключ-значение</li>
 *     <li>Ключом является UUID товара</li>
 *     <li>Значением является количество единиц товара</li>
 * </ul>
 *
 * Поля класса:
 * <ul>
 *     <li>{@code cartId} - уникальный идентификатор корзины</li>
 *     <li>{@code products} - карта товаров с их количеством</li>
 * </ul>
 */
@Data
public class ShoppingCartDto {
    @NotNull
    private UUID cartId;
    @NotNull
    private Map<UUID, Integer> products;
}
