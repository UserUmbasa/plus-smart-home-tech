package ru.yandex.practicum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Обработка ошибки недостаточного количества товара
    @ExceptionHandler(ProductInShoppingCartLowQuantityInWarehouse.class)
    public ResponseEntity<ErrorResponseDto> handleProductQuantityError(ProductInShoppingCartLowQuantityInWarehouse ex) {
        ErrorResponseDto error = new ErrorResponseDto(
                HttpStatus.BAD_REQUEST.value(), // 400
                "LOW_QUANTITY", // Уникальный код ошибки
                ex.getMessage(), // Сообщение из исключения
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Обработка ошибки отсутствия доставки
    @ExceptionHandler(NoDeliveryFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleDeliveryNotFoundError(NoDeliveryFoundException ex) {
        ErrorResponseDto error = new ErrorResponseDto(
                HttpStatus.NOT_FOUND.value(), // 404
                "DELIVERY_NOT_FOUND", // Уникальный код ошибки
                "Не найдена доставка", // Сообщение об ошибке
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // Обработка ошибки дублирования товара на складе
    @ExceptionHandler(SpecifiedProductAlreadyInWarehouseException.class)
    public ResponseEntity<ErrorResponseDto> handleDuplicateProductError(SpecifiedProductAlreadyInWarehouseException ex) {
        ErrorResponseDto error = new ErrorResponseDto(
                HttpStatus.BAD_REQUEST.value(), // 400
                "DUPLICATE_PRODUCT", // Уникальный код ошибки
                "Ошибка, товар с таким описанием уже зарегистрирован на складе", // Сообщение об ошибке
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Обработка ошибки расчёта стоимости товаров
    @ExceptionHandler(NotEnoughInfoInOrderToCalculateException.class)
    public ResponseEntity<ErrorResponseDto> handleNotEnoughInfoError(NotEnoughInfoInOrderToCalculateException ex) {
        ErrorResponseDto error = new ErrorResponseDto(
                HttpStatus.BAD_REQUEST.value(), // 400
                "NOT_ENOUGH_INFO_FOR_CALCULATION", // Уникальный код ошибки
                "Ошибка, Недостаточно информации в заказе для расчёта", // Сообщение об ошибке
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
