package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.api.shoppingStore.ShoppingStoreOperations;
import ru.yandex.practicum.dto.shoppingStore.ProductCategory;
import ru.yandex.practicum.dto.shoppingStore.ProductDto;
import ru.yandex.practicum.dto.shoppingStore.QuantityState;
import ru.yandex.practicum.service.ShoppingStoreService;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/shopping-store")
public class ShoppingStoreController implements ShoppingStoreOperations {
    private final ShoppingStoreService shoppingStoreService;

    @Override
    public ProductDto createNewProduct(ProductDto productDto) {
        return shoppingStoreService.addProduct(productDto);
    }

    @Override
    public ProductDto getProduct(UUID productId) {
        return shoppingStoreService.getProductById(productId);
    }

    @Override
    public List<ProductDto> getProducts(ProductCategory category, Pageable pageable) {
        return shoppingStoreService.getProducts(category, pageable);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        return shoppingStoreService.updateProduct(productDto);
    }

    @Override
    public boolean removeProductFromStore(UUID productId) {
        return shoppingStoreService.removeProduct(productId);
    }

    @Override
    public boolean setProductQuantityState(UUID productId, QuantityState quantityState) {
        return shoppingStoreService.updateQuantityState(productId, quantityState);
    }
}
