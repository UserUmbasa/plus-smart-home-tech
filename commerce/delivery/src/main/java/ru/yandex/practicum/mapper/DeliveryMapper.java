package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.model.Delivery;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DeliveryMapper {

    Delivery mapToDelivery(DeliveryDto deliveryDto);

    DeliveryDto mapToDeliveryDto(Delivery delivery);
}
