package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.shoppingCart.ShoppingCartDto;
import ru.yandex.practicum.dto.warehouse.*;
import ru.yandex.practicum.exception.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.yandex.practicum.exception.SpecifiedProductAlreadyInWarehouseException;
import ru.yandex.practicum.mapper.WarehouseMapper;
import ru.yandex.practicum.model.OrderBooking;
import ru.yandex.practicum.model.WarehouseProduct;
import ru.yandex.practicum.repository.BookingRepository;
import ru.yandex.practicum.repository.WarehouseRepository;

import java.security.SecureRandom;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final BookingRepository bookingRepository;
    private final WarehouseMapper warehouseMapper;
    private final AddressDto warehouseAddress = initAddress();
    private final WarehouseMapper mapper;


    @Transactional
    @Override // Добавить новый товар на склад.
    public void newProductInWarehouse(NewProductInWarehouseRequest request) {
        warehouseRepository.findById(request.getProductId())
                .ifPresent(product -> {
                    throw new SpecifiedProductAlreadyInWarehouseException("Товар уже находится на складе");
                });
        log.info("Добавляем новый товар в перечень - {}", request);
        warehouseRepository.save(warehouseMapper.toEntity(request));
    }

    @Transactional
    @Override // проверить что количество товаров на складе достаточно для данной корзины продуктов.
    public BookedProductsDto checkProductQuantityEnoughForShoppingCart(ShoppingCartDto cartDto) {

        Map<UUID, Integer> products = cartDto.getProducts();
        List<WarehouseProduct> availableProductsList = warehouseRepository.findAllById(products.keySet());
        Map<UUID, WarehouseProduct> availableProductsMap = availableProductsList.stream()
                .collect(Collectors.toMap(WarehouseProduct::getProductId, Function.identity()));
        BookedProductsDto bookedProductsDto = new BookedProductsDto();
        List<String> insufficientProducts = new ArrayList<>();
        for (Map.Entry<UUID, Integer> product : products.entrySet()) {
            UUID id = product.getKey();
            WarehouseProduct availableProduct = availableProductsMap.get(id);
            if (availableProduct == null) {
                throw new NoSpecifiedProductInWarehouseException("Такого товара нет на складе:" + product.getKey().toString());
            }
            if (availableProduct.getQuantity() >= product.getValue()) {
                Double volume = bookedProductsDto.getDeliveryVolume() + (availableProduct.getWidth() * availableProduct.getHeight() * availableProduct.getDepth()) * product.getValue();
                bookedProductsDto.setDeliveryVolume(volume);
                Double weight = bookedProductsDto.getDeliveryWeight() + (availableProduct.getWeight()) * product.getValue();
                bookedProductsDto.setDeliveryWeight(weight);
                if (availableProduct.getFragile()) {
                    bookedProductsDto.setFragile(true);
                }
            } else {
                String message = availableProduct.getProductId() + " недостаточно на складе";
                insufficientProducts.add(message);
            }
        }
        if (!insufficientProducts.isEmpty()) {
            String errorMessage = "Недостаточное количество для продуктов: " + String.join(", ", insufficientProducts);
            throw new ProductInShoppingCartLowQuantityInWarehouse(errorMessage);
        }
        return bookedProductsDto;
    }

    @Transactional
    @Override // Принять товар на склад.
    public void addProductToWarehouse(AddProductToWarehouseRequest request) {
        WarehouseProduct product = warehouseRepository.findById(request.getProductId())
                .orElseThrow(() -> new NoSpecifiedProductInWarehouseException("Такого товара нет в перечне на складе:" + request.getProductId()));
        Integer oldQuantity = product.getQuantity();
        Integer newQuantity = oldQuantity + request.getQuantity();
        product.setQuantity(newQuantity);
        warehouseRepository.save(product);
        log.info("Добавлено {} единиц продукта {} на склад. Новое количество: {}",
                request.getQuantity(), request.getProductId(), product.getQuantity());
    }

    @Override
    public AddressDto getWarehouseAddress() {
        return warehouseAddress;
    }

    @Transactional
    @Override // Собрать товары к заказу для подготовки к отправке.
    public BookedProductsDto assemblyProductsForOrder(AssemblyProductsForOrderRequest request) {
        BookedProductsDto result = checkProductQuantityEnoughForShoppingCart(mapper.mapToShoppingCartDto(request));
        Map<UUID, Integer> products = request.getProducts();
        // выдернули с базы
        List<WarehouseProduct> warehouseProducts = warehouseRepository.findAllById(request.getProducts().keySet());
        Map<UUID, WarehouseProduct> productMap = warehouseProducts.stream()
                .collect(Collectors.toMap(WarehouseProduct::getProductId, product -> product));
        // Обновляем количество для каждого возвращаемого товара
        for (Map.Entry<UUID, Integer> entry : products.entrySet()) {
            UUID productId = entry.getKey();
            Integer quantityToAdd = entry.getValue();
            WarehouseProduct product = productMap.get(productId);
            product.setQuantity(product.getQuantity() - quantityToAdd);
        }
        // Сохраняем изменения пакетно
        warehouseRepository.saveAll(warehouseProducts);
        // сохранили в БД
        OrderBooking orderBooking = new OrderBooking();
        orderBooking.setOrderId(request.getOrderId());
        orderBooking.setProducts(products);
        bookingRepository.save(orderBooking);
        log.info("Товары к заказу для передачи в доставку собраны");
        return result;
    }

    @Transactional
    @Override // Передать товары в доставку. Вызывается из сервиса доставки
    public void shippedToDelivery(ShippedToDeliveryRequest request) {
        OrderBooking orderBooking = bookingRepository.findByOrderId(request.getOrderId());
        orderBooking.setDeliveryId(request.getDeliveryId());
        bookingRepository.save(orderBooking);
    }

    @Transactional
    @Override
    public void acceptReturn(Map<UUID, Integer> productsToReturn) {
        List<WarehouseProduct> warehouseProducts = warehouseRepository.findAllById(productsToReturn.keySet());
        Map<UUID, WarehouseProduct> productMap = warehouseProducts.stream()
                .collect(Collectors.toMap(WarehouseProduct::getProductId, product -> product));
        // Обновляем количество для каждого возвращаемого товара
        for (Map.Entry<UUID, Integer> entry : productsToReturn.entrySet()) {
            UUID productId = entry.getKey();
            Integer quantityToAdd = entry.getValue();
            WarehouseProduct product = productMap.get(productId);
            product.setQuantity(product.getQuantity() + quantityToAdd);
        }
        // Сохраняем изменения пакетно
        warehouseRepository.saveAll(warehouseProducts);
    }


    private AddressDto initAddress() {
        final String[] addresses = new String[]{"ADDRESS_1", "ADDRESS_2"};
        final String address = addresses[Random.from(new SecureRandom()).nextInt(0, 1)];
        return AddressDto.builder()
                .city(address)
                .street(address)
                .house(address)
                .country(address)
                .flat(address)
                .build();
    }
}
