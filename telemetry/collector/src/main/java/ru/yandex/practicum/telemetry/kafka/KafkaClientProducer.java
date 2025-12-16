package ru.yandex.practicum.telemetry.kafka;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.Producer;

/**
 * Интерфейс клиента-продюсера для работы с Kafka.
 * <p>
 * Данный интерфейс определяет основные операции для отправки сообщений в Kafka.
 * Он предоставляет доступ к продюсеру и управление его жизненным циклом.
 *
 * Основные возможности:
 * <ul>
 *     <li>Получение экземпляра продюсера для отправки сообщений</li>
 *     <li>Остановка работы продюсера</li>
 * </ul>
 *
 * Методы интерфейса:
 * <ul>
 *     <li>{@code getProducer()} - возвращает экземпляр продюсера для отправки сообщений</li>
 *     <li>{@code stop()} - останавливает работу продюсера</li>
 * </ul>
 */
public interface KafkaClientProducer {
    Producer<String, SpecificRecordBase> getProducer();

    void stop();
}
