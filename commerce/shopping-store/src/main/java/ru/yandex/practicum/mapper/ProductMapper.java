package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import ru.yandex.practicum.model.Product;
import ru.yandex.practicum.dto.shoppingStore.ProductDto;

@Mapper(componentModel = org.mapstruct.MappingConstants.ComponentModel.SPRING)
public abstract class ProductMapper {

    public abstract Product mapToProduct(ProductDto request);

    public abstract ProductDto mapToProductDto(Product response);
}
