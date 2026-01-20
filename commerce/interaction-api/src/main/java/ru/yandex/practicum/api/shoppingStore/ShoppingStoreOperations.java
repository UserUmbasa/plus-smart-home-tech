package ru.yandex.practicum.api.shoppingStore;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.shoppingStore.ProductCategory;
import ru.yandex.practicum.dto.shoppingStore.ProductDto;
import ru.yandex.practicum.dto.shoppingStore.ProductPageDto;
import ru.yandex.practicum.dto.shoppingStore.QuantityState;
import java.util.UUID;

/**
 *  Памятка
 * • url в @FeignClient: Используется для задания полного базового URL (с хостом и портом), если вы не используете обнаружение сервисов или хотите явно указать адрес.
 * • name в @FeignClient: Используется для обнаружения сервисов (через Spring Cloud, Eureka, Consul и т.д.). Feign запрашивает у системы обнаружения реальный адрес сервиса по этому имени.
 * • path в @FeignClient: Используется для задания общего префикса пути для всех методов клиента. Это полезно, когда все эндпоинты сервиса начинаются с одинаковой части URL.
 */
@FeignClient(name = "shopping-store", path = "/api/v1/shopping-store")
public interface ShoppingStoreOperations {

    @PutMapping
    public ProductDto createNewProduct(@RequestBody @Valid ProductDto productDto);

    @GetMapping("/{productId}")
    public ProductDto getProduct(@PathVariable @NotNull UUID productId);

    @GetMapping
    public ProductPageDto getProducts(@RequestParam(name = "category") @NotNull ProductCategory category, Pageable pageable);

    @PostMapping
    public ProductDto updateProduct(@RequestBody @Valid ProductDto productDto);

    @PostMapping("/removeProductFromStore")
    public boolean removeProductFromStore(@RequestBody @NotNull UUID productId);

    @PostMapping("/quantityState")
    public boolean setProductQuantityState(
            @RequestParam("productId") UUID productId,
            @RequestParam("quantityState") QuantityState quantityState
    );
}
