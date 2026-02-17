package ru.yandex.practicum.dto.delivery;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.yandex.practicum.dto.warehouse.AddressDto;

import java.util.UUID;

/**
 * DTO-объект, представляющий информацию о доставке.
 * <p>
 * DTO включает все ключевые характеристики доставки, необходимые для
 * отображения и обработки на клиентских и серверных компонентах.
 * <p>
 * Поля DTO:
 * <ul>
 *     <li>{@code deliveryId} - уникальный идентификатор доставки</li>
 *     <li>{@code fromAddress} - обязательный адрес отправления</li>
 *     <li>{@code toAddress} - обязательный адрес доставки</li>
 *     <li>{@code orderId} - обязательный идентификатор связанного заказа</li>
 *     <li>{@code deliveryState} - текущее состояние доставки</li>
 * </ul>
 */
@Data
public class DeliveryDto {
    /**
     * Уникальный идентификатор доставки
     */
    private UUID deliveryId;

    /**
     * Адрес отправления
     * {@code @NotNull} - поле обязательно должно быть заполнено
     * {@code @Valid} - адрес должен проходить валидацию
     */
    @NotNull
    @Valid
    private AddressDto fromAddress;

    /**
     * Адрес доставки
     * {@code @NotNull} - поле обязательно должно быть заполнено
     * {@code @Valid} - адрес должен проходить валидацию
     */
    @NotNull
    @Valid
    private AddressDto toAddress;

    /**
     * Идентификатор связанного заказа
     * {@code @NotNull} - поле обязательно должно быть заполнено
     */
    @NotNull
    private UUID orderId;

    /**
     * Текущее состояние доставки
     * Может принимать значения из перечисления DeliveryState
     */
    private DeliveryState deliveryState;
}

