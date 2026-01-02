package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.model.ProductDto;

@FeignClient(name = "shopping-store", url = "/api/v1/shopping-store")
public interface ShoppingStoreOperations {

    @PutMapping
    public ProductDto createNewProduct(@RequestBody @Valid ProductDto productDto);
}
