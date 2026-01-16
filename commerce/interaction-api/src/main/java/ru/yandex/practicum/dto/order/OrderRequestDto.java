package ru.yandex.practicum.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.yandex.practicum.dto.shoppingCart.ShoppingCartDto;
import ru.yandex.practicum.dto.warehouse.AddressDto;

/**
 * DTO-объект запроса на создание заказа.
 * <p>
 * Класс представляет собой объект передачи данных (Data Transfer Object),
 * используемый для создания нового заказа в системе.
 * Содержит минимально необходимую информацию для формирования заказа.
 * <p>
 * Поля DTO:
 * <ul>
 *     <li>{@code shoppingCart} - обязательный объект корзины товаров,
 *     содержащий список товаров для заказа</li>
 *     <li>{@code address} - обязательный объект адреса доставки,
 *     содержащий информацию о месте доставки заказа</li>
 * </ul>
 */
@Data
public class OrderRequestDto {
    @NotNull
    private ShoppingCartDto shoppingCart;

    @NotNull
    private AddressDto address;
}
