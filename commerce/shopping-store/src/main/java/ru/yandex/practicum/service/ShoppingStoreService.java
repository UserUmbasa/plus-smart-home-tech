package ru.yandex.practicum.service;

import ru.yandex.practicum.model.ProductDto;

import java.util.UUID;

public interface ShoppingStoreService {
    //List<ProductDto> getProducts(ProductCategory category, Pageable pageable);

    ProductDto addProduct(ProductDto productDto);

    //ProductDto updateProduct(ProductDto productDto);

    //boolean updateQuantityState(SetProductQuantityStateRequest request);

    //boolean removeProduct(UUID productId);

    ProductDto getProductById(UUID productId);
}
