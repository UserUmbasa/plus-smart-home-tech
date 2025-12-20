package ru.yandex.practicum.telemetry.service.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.ClimateSensorProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.ClimateSensorAvro;
import ru.yandex.practicum.telemetry.kafka.KafkaClientProducer;
import ru.yandex.practicum.telemetry.sensor.model.ClimateSensorEvent;
import ru.yandex.practicum.telemetry.sensor.model.SensorEvent;
import ru.yandex.practicum.telemetry.sensor.type.SensorEventType;

/**
 * Обработчик событий климатического датчика.
 * <p>
 * Данный класс реализует логику обработки событий климатического датчика.
 * Он преобразует событие в формат Avro и отправляет его в Kafka.
 *
 * Основные характеристики:
 * <ul>
 *     <li>Наследуется от {@code BaseSensorEventHandler} с типом записи {@code ClimateSensorAvro}</li>
 *     <li>Обрабатывает события климатического датчика</li>
 *     <li>Преобразует данные о CO2, влажности и температуре</li>
 * </ul>
 *
 * Компоненты класса:
 * <ul>
 *     <li>Конструктор принимает {@code KafkaClientProducer} для отправки сообщений</li>
 *     <li>Метод {@code mapToAvro()} преобразует SensorEvent в ClimateSensorAvro</li>
 *     <li>Метод {@code getMessageType()} возвращает тип обрабатываемого события</li>
 * </ul>
 */
@Component
public class ClimateSensorEventHandler extends BaseSensorEventHandler<ClimateSensorAvro> {
    public ClimateSensorEventHandler(KafkaClientProducer producer) {
        super(producer);
    }

    @Override
    protected ClimateSensorAvro mapToAvro(SensorEventProto event) {
        ClimateSensorProto climateSensorEvent = event.getClimateSensorEvent();
        return ClimateSensorAvro.newBuilder()
                .setCo2Level(climateSensorEvent.getCo2Level())
                .setHumidity(climateSensorEvent.getHumidity())
                .setTemperatureC(climateSensorEvent.getTemperatureC())
                .build();
    }

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.CLIMATE_SENSOR_EVENT;
    }
}