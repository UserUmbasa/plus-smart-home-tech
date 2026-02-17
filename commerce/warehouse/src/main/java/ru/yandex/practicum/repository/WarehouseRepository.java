package ru.yandex.practicum.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.yandex.practicum.model.WarehouseProduct;
import java.util.UUID;

public interface WarehouseRepository extends JpaRepository<WarehouseProduct, UUID> {

    @Modifying
    @Query("UPDATE WarehouseProduct w SET w.quantity = w.quantity - :amount " +
            "WHERE w.productId = :id AND w.quantity >= :amount")
    void decreaseQuantity(@Param("id") UUID id, @Param("amount") Integer amount);
}
