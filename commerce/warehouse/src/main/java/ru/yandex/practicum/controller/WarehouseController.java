package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.api.warehouse.WarehouseClient;
import ru.yandex.practicum.dto.shoppingCart.ShoppingCartDto;
import ru.yandex.practicum.dto.warehouse.*;
import ru.yandex.practicum.service.WarehouseService;

import java.util.Map;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/warehouse")
public class WarehouseController implements WarehouseClient {
    private final WarehouseService warehouseService;

    @Override // PUT /api/v1/warehouse - Добавить новый товар на склад
    public void newProductInWarehouse(NewProductInWarehouseRequest request) {
        warehouseService.newProductInWarehouse(request);
    }

    @Override // POST /api/v1/warehouse/check - Проверка количества товаров на складе
    public BookedProductsDto checkProductQuantityEnoughForShoppingCart(ShoppingCartDto cartDto) {
        return warehouseService.checkProductQuantityEnoughForShoppingCart(cartDto);
    }

    @Override // POST /api/v1/warehouse/add - Принять товар на склад
    public void addProductToWarehouse(AddProductToWarehouseRequest request) {
        warehouseService.addProductToWarehouse(request);
    }

    @Override // GET /api/v1/warehouse/address - Предоставить адрес склада для расчёта доставки
    public AddressDto getWarehouseAddress() {
        return warehouseService.getWarehouseAddress();
    }

    @Override // POST /api/v1/warehouse/assembly - Собрать товары к заказу для подготовки к отправке"
    public BookedProductsDto assemblyProductsForOrder(AssemblyProductsForOrderRequest request) {
        return warehouseService.assemblyProductsForOrder(request);
    }

    @Override // POST /api/v1/warehouse/shipped - Передать товары в доставку
    public void shippedToDelivery(ShippedToDeliveryRequest request) {

    }

    @Override // POST /api/v1/warehouse/return - Принять возврат товаров на склад
    public void acceptReturn(Map<UUID, Integer> productsToReturn) {
        warehouseService.acceptReturn(productsToReturn);
    }
}
