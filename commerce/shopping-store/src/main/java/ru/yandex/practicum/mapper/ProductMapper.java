package ru.yandex.practicum.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ru.yandex.practicum.model.Product;
import ru.yandex.practicum.model.ProductDto;

@Mapper(componentModel = org.mapstruct.MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class ProductMapper {

    public abstract Product mapToProduct(ProductDto request);

    public abstract ProductDto mapToProductDto(Product response);
}
