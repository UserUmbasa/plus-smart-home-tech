package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.shoppingStore.*;
import ru.yandex.practicum.exception.ProductNotFoundException;
import ru.yandex.practicum.mapper.ProductMapper;
import ru.yandex.practicum.model.Product;
import ru.yandex.practicum.repository.ProductRepository;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShoppingStoreServiceImpl implements ShoppingStoreService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    @Override
    public ProductDto addProduct(ProductDto productDto) {
        Product product = productMapper.mapToProduct(productDto);
        product = productRepository.save(product);
        log.info("Сохранили товар в DB - {}", product);
        return productMapper.mapToProductDto(product);
    }

    @Transactional
    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        findProductByIdOrThrow(productDto.getProductId());
        Product result = productRepository.save(productMapper.mapToProduct(productDto));
        log.info("Обновили товар в DB - {}", result);
        return productMapper.mapToProductDto(result);
    }

    @Transactional
    @Override
    public boolean removeProduct(UUID productId) {
        Product product = findProductByIdOrThrow(productId);
        product.setProductState(ProductState.DEACTIVATE);
        productRepository.save(product);
        log.info("Деактивировали товар в DB - {}", product);
        return true;
    }

    @Transactional()
    @Override
    public ProductDto getProductById(UUID productId) {
        Product product = findProductByIdOrThrow(productId);
        log.info("Получили из DB товар {}", product);
        return productMapper.mapToProductDto(product);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductPageDto getProducts(ProductCategory category, Pageable pageable) {
        log.debug("Запрашиваем товары с категорией - {} и пагинацией - {}", category, pageable);
        List<Product> products = productRepository.findAllByProductCategory(category, pageable).getContent();
        log.debug("Получили из DB список товаров размером {}", products.size());
        List<ProductDto> productsDto = products.stream()
                .map(productMapper::mapToProductDto)
                .toList();
        return new ProductPageDto(productsDto, pageable.getSort());
    }

    @Transactional
    @Override
    public boolean updateQuantityState(UUID productId, QuantityState quantityState) {
        Product product = findProductByIdOrThrow(productId);
        product.setQuantityState(quantityState);
        productRepository.save(product);
        log.info("Обновили количество товара в DB - {}", product);
        return true;
    }

    private Product findProductByIdOrThrow(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Продукта с id " + productId + " не существует"));
    }
}
