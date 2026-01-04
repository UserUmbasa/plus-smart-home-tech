package ru.yandex.practicum.api.shoppingStore;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.shoppingStore.ProductCategory;
import ru.yandex.practicum.dto.shoppingStore.ProductDto;
import ru.yandex.practicum.dto.shoppingStore.QuantityState;
import java.util.List;
import java.util.UUID;

@FeignClient(name = "shopping-store", url = "/api/v1/shopping-store")
public interface ShoppingStoreOperations {

    @PutMapping
    public ProductDto createNewProduct(@RequestBody @Valid ProductDto productDto);

    @GetMapping("/{productId}")
    public ProductDto getProduct(@PathVariable @NotNull UUID productId);

    @GetMapping
    public List<ProductDto> getProducts(@RequestParam(name = "category") @NotNull ProductCategory category, Pageable pageable);

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
