package ru.yandex.practicum.api.shoppingStore;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.dto.shoppingStore.ProductCategory;
import ru.yandex.practicum.dto.shoppingStore.ProductDto;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "shopping-store", url = "/api/v1/shopping-store")
public interface ShoppingStoreOperations {

    @PutMapping
    public ProductDto createNewProduct(@RequestBody @Valid ProductDto productDto);

    @GetMapping
    public ProductDto getProduct(UUID productId);

    @GetMapping
    public List<ProductDto> getProducts(ProductCategory category, Pageable pageable);
}
