package ru.yandex.practicum.telemetry.service.handler.sensor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.telemetry.kafka.KafkaClientProducer;
import ru.yandex.practicum.telemetry.sensor.model.SensorEvent;

/**
 * Абстрактный базовый класс для обработчиков событий датчиков.
 * <p>
 * Данный класс предоставляет общую реализацию обработки событий датчиков,
 * включая преобразование в формат Avro и отправку в Kafka.
 * <p>
 * Класс является универсальным и может быть расширен для обработки
 * конкретных типов событий датчиков.
 */
@Slf4j
@RequiredArgsConstructor
public abstract class BaseSensorEventHandler<T extends SpecificRecordBase> implements SensorEventHandler {
    protected final KafkaClientProducer producer;

    @Value("${kafka.topic.sensor}")
    protected String topic;

    protected abstract T mapToAvro(SensorEvent event);

    @Override
    public void handle(SensorEvent event) {
        if (!event.getType().equals(getMessageType())) {
            throw new IllegalArgumentException("Неизвестный тип события: " + event.getType());
        }
        //преобразование события в Avro запись
        T payload = mapToAvro(event);

        SensorEventAvro eventAvro = SensorEventAvro.newBuilder()
                .setHubId(event.getHubId())
                .setId(event.getId())
                .setTimestamp(event.getTimestamp())
                .setPayload(payload)
                .build();

        ProducerRecord<String, SpecificRecordBase> record = new ProducerRecord<>(
                topic,
                null,
                event.getTimestamp().toEpochMilli(),
                eventAvro.getHubId(),
                eventAvro);

        // отправка в кафку
        producer.getProducer().send(record);
        log.info("Отправили в Kafka: {}", record);
    }
}