package ru.yandex.practicum.aggregator;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.aggregator.deserializer.SensorEventDeserializer;
import ru.yandex.practicum.aggregator.serializer.AvroSerializer;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.time.Duration;
import java.util.*;

/**
 * Класс AggregationStarter, ответственный за запуск агрегации данных.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AggregationStarter {

    private KafkaConsumer<String, SensorEventAvro> consumer;
    private KafkaProducer<String, SensorsSnapshotAvro> producer;
    private final Map<String, SensorsSnapshotAvro> snapshots = new HashMap<>();
    private static final Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();

    @Value("${kafka.kafka-servers}")
    private String bootstrapServers;
    @Value("${kafka.group-id}")
    private String groupId;
    @Value("${kafka.topic-sensors}")
    private String topicSensors;
    @Value("${kafka.topic-snapshots}")
    private String topicSnapshots;


    @PostConstruct
    public void init() {
        Properties configConsumer = new Properties();
        configConsumer.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configConsumer.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        configConsumer.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        configConsumer.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, SensorEventDeserializer.class.getName());
        consumer = new KafkaConsumer<>(configConsumer);

        Properties configProducer = new Properties();
        configProducer.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProducer.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configProducer.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AvroSerializer.class.getName());
        producer = new KafkaProducer<>(configProducer);
    }

    /**
     * Метод для начала процесса агрегации данных.
     * Подписывается на топики для получения событий от датчиков,
     * формирует снимок их состояния и записывает в кафку.
     */
    public void start() {
        try {
            consumer.subscribe(List.of(topicSensors));
            log.info("Подписка на топик {}", topicSensors);

            while (true) {
                ConsumerRecords<String, SensorEventAvro> records = consumer.poll(Duration.ofMillis(1000));

                int count = 0;
                for (ConsumerRecord<String, SensorEventAvro> record : records) {
                    handleRecord(record);
                    manageOffsets(record, count, consumer);
                    count++;
                }
                consumer.commitAsync();
            }

        } catch (WakeupException ignored) {

        } catch (Exception e) {
            log.error("Ошибка во время обработки событий от датчиков", e);
        } finally {
            try {
                producer.flush();
                log.info("Все данные отправлены в Kafka");
                consumer.commitSync();
                log.info("Все смещения зафиксированы");
            } finally {
                log.info("Закрываем консьюмер");
                consumer.close();
                log.info("Закрываем продюсер");
                producer.close();
            }
        }
    }

    private static void manageOffsets(ConsumerRecord<String, SensorEventAvro> record, int count, KafkaConsumer<String, SensorEventAvro> consumer) {
        // обновляем текущий оффсет для топика-партиции
        currentOffsets.put(
                new TopicPartition(record.topic(), record.partition()),
                new OffsetAndMetadata(record.offset() + 1)
        );

        if(count % 10 == 0) {
            consumer.commitAsync(currentOffsets, (offsets, exception) -> {
                if(exception != null) {
                    log.warn("Ошибка во время фиксации оффсетов: {}", offsets, exception);
                }
            });
        }
    }

    private void handleRecord(ConsumerRecord<String, SensorEventAvro> record) throws InterruptedException {
        log.info("топик = {}, партиция = {}, смещение = {}, значение: {}\n",
                record.topic(), record.partition(), record.offset(), record.value());
        try {
            Optional<SensorsSnapshotAvro> mayBeSnapshot = updateState(record.value());

            if (mayBeSnapshot.isPresent()) {
                SensorsSnapshotAvro snapshot = mayBeSnapshot.get();
                ProducerRecord<String, SensorsSnapshotAvro> producerRecord =
                        new ProducerRecord<>(topicSnapshots, snapshot.getHubId(), snapshot);

                producer.send(producerRecord, (metadata, exception) -> {
                    if (exception != null) {
                        log.error("Ошибка при отправке сообщения в Kafka: {}", exception.getMessage(), exception);
                    } else {
                        log.info("Сообщение={} отправлено в Kafka: топик={}, смещение={}",
                                producerRecord, metadata.topic(), metadata.offset());
                    }
                });
            }
        } catch (Exception e) {
            log.error("Ошибка при обработке записи: ключ={}, значение={}", record.key(), record.value(), e);
        }
    }

    public Optional<SensorsSnapshotAvro> updateState(SensorEventAvro event) {
        SensorsSnapshotAvro snapshot = snapshots.get(event.getHubId());
        if (snapshot == null) {
            snapshot = new SensorsSnapshotAvro();
            snapshot.setHubId(event.getHubId());
            snapshot.setTimestamp(event.getTimestamp());
            snapshot.setSensorsState(new HashMap<>());
        }

        SensorStateAvro oldState = snapshot.getSensorsState().get(event.getId());
        if (oldState != null) {
            // Если oldState произошёл позже, чем event
            // или данные совпадают, то обновление не требуется
            if (oldState.getTimestamp().isAfter(event.getTimestamp()) ||
                    oldState.getData().equals(event.getPayload())) {
                return Optional.empty();
            }
        }

        SensorStateAvro newState = new SensorStateAvro();
        newState.setTimestamp(event.getTimestamp());
        newState.setData(event.getPayload());
        snapshot.getSensorsState().put(event.getId(), newState);
        snapshot.setTimestamp(event.getTimestamp());
        snapshots.put(event.getHubId(), snapshot);
        return Optional.of(snapshot);
    }
}