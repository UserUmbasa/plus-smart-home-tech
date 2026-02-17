package ru.yandex.practicum.dto.shoppingStore;

/**
 * Перечисление состояний товара в системе.
 * <p>
 * Возможные состояния:
 * <ul>
 *     <li>{@code ACTIVE} - товар активен и доступен для заказа</li>
 *     <li>{@code DEACTIVATE} - товар временно недоступен для заказа</li>
 * </ul>
 */
public enum ProductState {
    ACTIVE, DEACTIVATE
}
