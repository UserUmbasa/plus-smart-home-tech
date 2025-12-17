package ru.yandex.practicum.telemetry.service.handler.hub;

import ru.yandex.practicum.telemetry.hub.model.HubEvent;
import ru.yandex.practicum.telemetry.hub.type.HubEventType;

/**
 * Интерфейс обработчика событий хаба.
 * <p>
 * Данный интерфейс определяет контракт для всех обработчиков событий в системе.
 * Каждый обработчик должен реализовать два основных метода:
 * <ul>
 *     <li>Определение типа события через метод {@code getMessageType()}</li>
 *     <li>Обработку события через метод {@code handle()}</li>
 * </ul>
 *
 * Основные методы интерфейса:
 * <ul>
 *     <li>{@code getMessageType()} - возвращает тип события, который обрабатывает данный обработчик</li>
 *     <li>{@code handle(HubEvent event)} - обрабатывает входящее событие хаба</li>
 * </ul>
 */
public interface HubEventHandler {
    HubEventType getMessageType();
    void handle(HubEvent event);
}
