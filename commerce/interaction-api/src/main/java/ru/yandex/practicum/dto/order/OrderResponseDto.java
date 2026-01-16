package ru.yandex.practicum.dto.order;

import ru.yandex.practicum.dto.warehouse.AddressDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class OrderResponseDto {
    private UUID orderId;
    private UUID shoppingCartId;
    private Map<UUID, Integer> products;
    private UUID paymentId;
    private UUID deliveryId;
    private OrderState state;
    private Double deliveryWeight;
    private Double deliveryVolume;
    private Boolean fragile;
    private BigDecimal totalPrice;
    private BigDecimal deliveryPrice;
    private BigDecimal productPrice;


    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private AddressDto deliveryAddress;

}
