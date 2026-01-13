package ru.yandex.practicum.dto.warehouse;

import lombok.Builder;
import lombok.Data;

/**
 * DTO-объект предназначен для хранения и передачи информации о физическом адресе.
 * Содержит основные компоненты адреса, необходимые для идентификации местоположения.
 * <ul></ul>
 * Поля класса:
 * <ul>
 *     <li>{@code country} - страна</li>
 *     <li>{@code city} - город</li>
 *     <li>{@code street} - улица</li>
 *     <li>{@code house} - номер дома</li>
 *     <li>{@code flat} - номер квартиры/помещения</li>
 * </ul>
 */
@Builder
@Data
public class AddressDto {
    private String country;
    private String city;
    private String street;
    private String house;
    private String flat;
}
