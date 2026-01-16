package ru.yandex.practicum.api.order;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.order.OrderRequestDto;
import ru.yandex.practicum.dto.order.OrderResponseDto;

@FeignClient(name = "client", url = "/api/v1/client")
public interface OrderClient {

    @PutMapping
    public OrderResponseDto createNewOrder(@RequestBody @Valid OrderRequestDto orderRequestDto);

//    @GetMapping("/{productId}")
//    public ProductDto getProduct(@PathVariable @NotNull UUID productId);
//
//    @GetMapping
//    public ProductPageDto getProducts(@RequestParam(name = "category") @NotNull ProductCategory category, Pageable pageable);
//
//    @PostMapping
//    public ProductDto updateProduct(@RequestBody @Valid ProductDto productDto);
//
//    @PostMapping("/removeProductFromStore")
//    public boolean removeProductFromStore(@RequestBody @NotNull UUID productId);
//
//    @PostMapping("/quantityState")
//    public boolean setProductQuantityState(
//            @RequestParam("productId") UUID productId,
//            @RequestParam("quantityState") QuantityState quantityState
//    );


}
