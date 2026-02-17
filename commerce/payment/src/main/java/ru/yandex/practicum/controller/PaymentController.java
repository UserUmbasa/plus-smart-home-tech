package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.api.payment.PaymentOperations;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.payment.PaymentDto;
import ru.yandex.practicum.service.PaymentService;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController implements PaymentOperations {
    private final PaymentService paymentService;

    @Override // POST /api/v1/payment/productCost - Расчёт стоимости товаров в заказе
    public BigDecimal productCost(OrderDto orderDto) {
        return paymentService.productCost(orderDto);
    }

    @Override // POST /api/v1/payment/totalCost - Расчёт полной стоимости заказа
    public BigDecimal getTotalCost(OrderDto orderDto) {
        return paymentService.getTotalCost(orderDto);
    }

    @Override // POST /api/v1/payment - Формирование оплаты для заказа
    public PaymentDto payment(OrderDto orderDto) {
        return paymentService.payment(orderDto);
    }

    @Override // POST /api/v1/payment/refund - Метод для эмуляции успешной оплаты платежного шлюза
    public void paymentSuccess(UUID paymentId) {
        paymentService.paymentSuccess(paymentId);
    }

    @Override // POST api/v1/payment/failed - Метод для эмуляции отказа в оплате платежного шлюза
    public void paymentFailed(UUID paymentId) {
        paymentService.paymentFailed(paymentId);
    }
}
