package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.shoppingStore.ProductCategory;
import ru.yandex.practicum.exception.ProductNotFoundException;
import ru.yandex.practicum.mapper.ProductMapper;
import ru.yandex.practicum.model.Product;
import ru.yandex.practicum.dto.shoppingStore.ProductDto;
import ru.yandex.practicum.repository.ProductRepository;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShoppingStoreServiceImpl implements ShoppingStoreService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        Product product = productMapper.mapToProduct(productDto);
        productRepository.save(product);
        return productMapper.mapToProductDto(product);
    }

    @Override
    public ProductDto getProductById(UUID productId) {
        log.debug("Запрашиваем товар с ID: {}", productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Продукта с id " + productId + " не существует"));
        log.debug("Получили из DB товар {}", product);
        return productMapper.mapToProductDto(product);
    }

    @Override
    public List<ProductDto> getProducts(ProductCategory category, Pageable pageable) {
        log.debug("Запрашиваем товары с категорией - {} и пагинацией - {}", category, pageable);
        List<Product> products = productRepository.findAllByProductCategory(category, pageable).getContent();
        log.debug("Получили из DB список товаров размером {}", products.size());
        return products.stream()
                .map(productMapper::mapToProductDto)
                .toList();
    }

}
