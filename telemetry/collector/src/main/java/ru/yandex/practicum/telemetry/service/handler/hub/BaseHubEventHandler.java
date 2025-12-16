package ru.yandex.practicum.telemetry.service.handler.hub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.telemetry.hub.model.HubEvent;
import ru.yandex.practicum.telemetry.kafka.KafkaClientProducer;

/**
 * Базовый абстрактный класс для обработчиков событий хаба.
 * <p>
 * Данный класс предоставляет базовую реализацию обработки событий хаба, включая:
 * <ul>
 *     <li>Преобразование событий в формат Avro</li>
 *     <li>Отправку сообщений в Kafka</li>
 *     <li>Логирование операций</li>
 * </ul>
 *
 * Класс является абстрактным и требует реализации метода mapToAvro() в конкретных обработчиках.
 *
 * Основные компоненты класса:
 * <ul>
 *     <li>{@code producer} - клиент-продюсер для отправки сообщений в Kafka</li>
 *     <li>{@code topic} - топик Kafka для отправки событий хаба. Значение конфигурируется через свойство kafka.topic.hub</li>
 * </ul>
 */
@Slf4j
@RequiredArgsConstructor
public abstract class BaseHubEventHandler<T extends SpecificRecordBase> implements HubEventHandler {
    protected final KafkaClientProducer producer;

    @Value("${kafka.topic.hub}")
    protected String topic;

    protected abstract T mapToAvro(HubEvent event);

    @Override
    public void handle(HubEvent event) {
        if (!event.getType().equals(getMessageType())) {
            throw new IllegalArgumentException("Неизвестный тип события: " + event.getType());
        }

        //преобразование события в Avro запись
        T payload = mapToAvro(event);

        HubEventAvro eventAvro = HubEventAvro.newBuilder()
                .setHubId(event.getHubId())
                .setTimestamp(event.getTimestamp())
                .setPayload(payload)
                .build();

        ProducerRecord<String, SpecificRecordBase> record = new ProducerRecord<>(
                topic,
                null,
                event.getTimestamp().toEpochMilli(),
                eventAvro.getHubId(),
                eventAvro);

        producer.getProducer().send(record);

        log.info("Отправили в Kafka: {}", record);
    }
}

