package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.dto.warehouse.NewProductInWarehouseRequest;
import ru.yandex.practicum.model.WarehouseProduct;

/**
 * Mapper-интерфейс для преобразования объектов между DTO и entity слоями.
 * <p>
 * Данный интерфейс отвечает за маппинг объектов NewProductInWarehouseRequest в сущности WarehouseProduct.
 * Использует библиотеку MapStruct для автоматического создания кода маппинга.
 *
 * Основные характеристики:
 * <ul>
 *     <li>Настроен как Spring-компонент</li>
 *     <li>Содержит правила преобразования полей между объектами</li>
 *     <li>Реализует маппинг с учетом вложенных объектов</li>
 * </ul>
 *
 * Особенности маппинга:
 * <ul>
 *     <li>Игнорируется поле quantity при преобразовании</li>
 *     <li>Размеры (depth, width, height) берутся из вложенного объекта DimensionDto</li>
 * </ul>
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WarehouseMapper {

    @Mapping(target = "quantity", ignore = true)
    @Mapping(target = "depth", source = "dto.dimension.depth")
    @Mapping(target = "width", source = "dto.dimension.width")
    @Mapping(target = "height", source = "dto.dimension.height")
    WarehouseProduct toEntity(NewProductInWarehouseRequest dto);

}
