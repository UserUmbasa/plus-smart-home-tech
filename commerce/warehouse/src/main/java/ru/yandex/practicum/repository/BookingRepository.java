package ru.yandex.practicum.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.model.OrderBooking;

import java.util.UUID;

public interface BookingRepository extends JpaRepository<OrderBooking, UUID> {

    OrderBooking findByOrderId(@NotNull UUID orderId);
}
