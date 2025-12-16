package ru.yandex.practicum.telemetry.kafka;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Конфигурация продюсера Kafka.
 * <p>
 * Данный класс отвечает за создание и настройку продюсера Kafka для отправки сообщений.
 * Он содержит логику инициализации продюсера с необходимыми настройками и конфигурацией.
 *
 * Основные компоненты конфигурации:
 * <ul>
 *     <li>Создание и настройка продюсера Kafka</li>
 *     <li>Управление жизненным циклом продюсера</li>
 *     <li>Сериализация данных в Avro формат</li>
 * </ul>
 *
 * Компоненты класса:
 * <ul>
 *     <li>{@code bootstrapServer} - адрес сервера Kafka</li>
 *     <li>{@code producer} - экземпляр продюсера Kafka</li>
 *     <li>Метод {@code getProducer()} - возвращает экземпляр продюсера</li>
 *     <li>Метод {@code initProducer()} - инициализирует продюсера с настройками</li>
 *     <li>Метод {@code stop()} - корректно завершает работу продюсера</li>
 * </ul>
 */
@Configuration
public class KafkaProducerConfig {

    @Bean
    KafkaClientProducer getProducer() {
        return new KafkaClientProducer() {
            private Producer<String, SpecificRecordBase> producer;
            @Value("${kafka.bootstrap.server}")
            private String bootstrapServer;

            @Override
            public Producer<String, SpecificRecordBase> getProducer() {
                if (producer == null) {
                    initProducer();
                }
                return producer;
            }

            private void initProducer() {
                Properties config = new Properties();
                config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
                config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
                config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GeneralAvroSerializer.class);

                producer = new KafkaProducer<>(config);
            }

            @Override
            public void stop() {
                if (producer != null) {
                    producer.flush();
                    producer.close();
                }
            }
        };
    }
}
