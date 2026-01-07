package ru.yandex.practicum.api.warehouse;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.dto.shoppingCart.ShoppingCartDto;
import ru.yandex.practicum.dto.warehouse.AddProductToWarehouseRequest;
import ru.yandex.practicum.dto.warehouse.AddressDto;
import ru.yandex.practicum.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.dto.warehouse.NewProductInWarehouseRequest;

@FeignClient(name = "warehouse",
        path = "/api/v1/warehouse",
        fallback = WarehouseFeignClientFallback.class)
@CircuitBreaker(name = "warehouse")
@Primary
public interface WarehouseClient {

    @PutMapping
    public void newProductInWarehouse(@RequestBody @Valid NewProductInWarehouseRequest request);

    @PostMapping("/check")
    public BookedProductsDto checkProductQuantityEnoughForShoppingCart (@RequestBody @Valid ShoppingCartDto cartDto);

    @PostMapping("/add")
    public void addProductToWarehouse(@RequestBody @Valid AddProductToWarehouseRequest request);

    @GetMapping("/address")
    public AddressDto getWarehouseAddress();
}
