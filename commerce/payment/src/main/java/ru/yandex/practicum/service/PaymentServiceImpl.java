package ru.yandex.practicum.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.api.order.OrderOperations;
import ru.yandex.practicum.api.shoppingStore.ShoppingStoreOperations;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.payment.PaymentDto;
import ru.yandex.practicum.dto.payment.PaymentState;
import ru.yandex.practicum.dto.shoppingStore.ProductDto;
import ru.yandex.practicum.exception.NoOrderFoundException;
import ru.yandex.practicum.exception.NotEnoughInfoInOrderToCalculateException;
import ru.yandex.practicum.model.Payment;
import ru.yandex.practicum.repository.PaymentRepository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final ShoppingStoreOperations shoppingStore;
    private final OrderOperations orderOperations;

    private static final BigDecimal NDS_MULTIPLIER = BigDecimal.valueOf(0.1); // 10% налог

    @Transactional
    @Override // Расчёт стоимости товаров в заказе (просто вернуть, не вносить изменения в productPrice?)
    public BigDecimal productCost(OrderDto orderDto) {
        BigDecimal productCost = BigDecimal.ZERO;
        Map<UUID, Integer> products = orderDto.getProducts();
        if (products.isEmpty()) {
            throw new NotEnoughInfoInOrderToCalculateException("Недостаточно информации в заказе для расчёта");
        }
        for (var entry : products.entrySet()) {
            UUID productId = entry.getKey();
            Integer amount = entry.getValue();
            ProductDto result = shoppingStore.getProduct(productId); // исключение прилетит из store
            productCost = productCost.add(result.getPrice().multiply(new BigDecimal(amount)));
        }
        log.info("Рассчитали стоимость товаров в заказе");
        return productCost;
    }

    @Transactional
    @Override // Расчёт полной стоимости заказа (товары + налог + доставка)
    public BigDecimal getTotalCost(OrderDto orderDto) {
        BigDecimal totalCost = BigDecimal.ZERO;
        // + стоимость товаров (c налогом)
        totalCost = totalCost.add(calculatePriceWithTax(orderDto.getProductPrice()));
        // + стоимость доставки
        totalCost = totalCost.add(orderDto.getDeliveryPrice());
        log.info("Рассчитали полную стоимость заказа");
        return totalCost;
    }

    @Transactional
    @Override // Формирование оплаты для заказа
    public PaymentDto payment(OrderDto orderDto) {
        Payment payment = new Payment();
        BigDecimal productCost = orderDto.getProductPrice();
        BigDecimal deliveryTotal = orderDto.getDeliveryPrice();
        BigDecimal totalCost = orderDto.getTotalPrice();
        if (productCost == null || deliveryTotal == null || totalCost == null) {
            throw new NotEnoughInfoInOrderToCalculateException("Недостаточно информации в заказе для расчёта");
        }
        payment.setFeeTotal(null); // дополнительные платежи не раскрыты
        payment.setProductTotal(productCost);
        payment.setTotalPayment(totalCost);
        payment.setDeliveryTotal(deliveryTotal);
        payment.setOrderId(orderDto.getOrderId());

        payment = paymentRepository.save(payment);

        PaymentDto result = new PaymentDto();
        result.setPaymentId(payment.getPaymentId());
        result.setTotalPayment(payment.getTotalPayment());
        result.setDeliveryTotal(payment.getDeliveryTotal());
        result.setFeeTotal(payment.getFeeTotal());

        return result;
    }

    @Override // Метод для эмуляции успешной оплаты платежного шлюза
    public void paymentSuccess(UUID paymentId) {
        try {
            Payment payment = paymentRepository.findById(paymentId)
                    .orElseThrow(() -> new NoOrderFoundException("Оплата не найдена"));
            // изменить статус на SUCCESS
            payment.setPaymentState(PaymentState.SUCCESS);
            paymentRepository.save(payment);
        } catch (NoOrderFoundException e) {
            throw new NoOrderFoundException(e.getMessage());
        }
        log.info("Успешная оплата заказа");
    }

    @Override // Метод для эмуляции отказа в оплате платежного шлюза
    public void paymentFailed(UUID paymentId) {
        try {
            Payment payment = paymentRepository.findById(paymentId)
                    .orElseThrow(() -> new NoOrderFoundException("Оплата не найдена"));
            // изменить статус на FAILED
            payment.setPaymentState(PaymentState.FAILED);
            paymentRepository.save(payment);
            // Вызвать изменения в сервисе заказов
            orderOperations.paymentFailed(payment.getOrderId());
        } catch (NoOrderFoundException e) {
            throw new NoOrderFoundException(e.getMessage());
        }
        log.info("Отказ в оплате заказа, изменили Payment, вызвали Order");
    }

    private BigDecimal calculatePriceWithTax(BigDecimal basePrice) {
        BigDecimal taxAmount = basePrice.multiply(NDS_MULTIPLIER);
        return basePrice.add(taxAmount);
    }

}
