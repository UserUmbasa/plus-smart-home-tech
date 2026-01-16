package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.order.OrderRequestDto;
import ru.yandex.practicum.dto.order.OrderResponseDto;
import ru.yandex.practicum.mapper.OrderMapper;
import ru.yandex.practicum.model.Order;
import ru.yandex.practicum.repository.OrderRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper mapper;

    @Override
    public OrderResponseDto newOrder(OrderRequestDto request) {
        Order order = mapper.mapToOrder(request);
        order = orderRepository.save(order);
        return mapper.mapToResponseDto(order);
    }


//    @Transactional
//    @Override // Добавить новый товар на склад.
//    public void newProductInWarehouse(NewProductInWarehouseRequest request) {
//        orderRepository.findById(request.getProductId())
//                .ifPresent(product -> {
//                    throw new SpecifiedProductAlreadyInWarehouseException("Товар уже находится на складе");
//                });
//        log.info("Добавляем новый товар в перечень - {}", request);
//        orderRepository.save(orderMapper.toEntity(request));
//    }
//
//    @Transactional
//    @Override // проверить что количество товаров на складе достаточно для данной корзины продуктов.
//    public BookedProductsDto checkProductQuantityEnoughForShoppingCart(ShoppingCartDto cartDto) {
//
//        Map<UUID, Integer> products = cartDto.getProducts();
//        List<Order> availableProductsList = orderRepository.findAllById(products.keySet());
//        Map<UUID, Order> availableProductsMap = availableProductsList.stream()
//                .collect(Collectors.toMap(Order::getProductId, Function.identity()));
//        BookedProductsDto bookedProductsDto = new BookedProductsDto();
//        List<String> insufficientProducts = new ArrayList<>();
//        for (Map.Entry<UUID, Integer> product : products.entrySet()) {
//            UUID id = product.getKey();
//            Order availableProduct = availableProductsMap.get(id);
//            if (availableProduct == null) {
//                throw new NoSpecifiedProductInWarehouseException("Такого товара нет на складе:" + product.getKey().toString());
//            }
//            if (availableProduct.getQuantity() >= product.getValue()) {
//                Double volume = bookedProductsDto.getDeliveryVolume() + (availableProduct.getWidth() * availableProduct.getHeight() * availableProduct.getDepth()) * product.getValue();
//                bookedProductsDto.setDeliveryVolume(volume);
//                Double weight = bookedProductsDto.getDeliveryWeight() + (availableProduct.getWeight()) * product.getValue();
//                bookedProductsDto.setDeliveryWeight(weight);
//                if (availableProduct.getFragile()) {
//                    bookedProductsDto.setFragile(true);
//                }
//            } else {
//                String message = availableProduct.getProductId() + " недостаточно на складе";
//                insufficientProducts.add(message);
//            }
//        }
//        if (!insufficientProducts.isEmpty()) {
//            String errorMessage = "Недостаточное количество для продуктов: " + String.join(", ", insufficientProducts);
//            throw new ProductInShoppingCartLowQuantityInWarehouse(errorMessage);
//        }
//        return bookedProductsDto;
//    }
//
//    @Transactional
//    @Override // Принять товар на склад.
//    public void addProductToWarehouse(AddProductToWarehouseRequest request) {
//        Order product = orderRepository.findById(request.getProductId())
//                .orElseThrow(() -> new NoSpecifiedProductInWarehouseException("Такого товара нет в перечне на складе:" + request.getProductId()));
//        Integer oldQuantity = product.getQuantity();
//        Integer newQuantity = oldQuantity + request.getQuantity();
//        product.setQuantity(newQuantity);
//        orderRepository.save(product);
//        log.info("Добавлено {} единиц продукта {} на склад. Новое количество: {}",
//                request.getQuantity(), request.getProductId(), product.getQuantity());
//    }
//
//    @Override
//    public AddressDto getWarehouseAddress() {
//        return warehouseAddress;
//    }
//
//    private AddressDto initAddress() {
//        final String[] addresses = new String[]{"ADDRESS_1", "ADDRESS_2"};
//        final String address = addresses[Random.from(new SecureRandom()).nextInt(0, addresses.length)];
//        return AddressDto.builder()
//                .city(address)
//                .street(address)
//                .house(address)
//                .country(address)
//                .flat(address)
//                .build();
//    }
}
