package ru.yandex.practicum.dto.warehouse;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

/**
 * DTO-объект запроса на передачу заказа в службу доставки.
 * <p>
 * Данный запрос применяется на этапе, когда заказ полностью собран и готов
 * к передаче в доставку. Содержит минимально необходимую информацию для
 * связи заказа с конкретной доставкой.
 * <p>
 * Поля DTO:
 * <ul>
 *     <li>{@code orderId} - обязательный уникальный идентификатор заказа,
 *     который необходимо передать в доставку</li>
 *     <li>{@code deliveryId} - обязательный уникальный идентификатор
 *     доставки, к которой привязывается заказ</li>
 * </ul>
 */
@Data
@AllArgsConstructor
public class ShippedToDeliveryRequest {
    /**
     * Уникальный идентификатор заказа
     * {@code @NotNull} - поле обязательно должно быть заполнено
     */
    @NotNull
    private UUID orderId;

    /**
     * Уникальный идентификатор доставки
     * {@code @NotNull} - поле обязательно должно быть заполнено
     */
    @NotNull
    private UUID deliveryId;
}