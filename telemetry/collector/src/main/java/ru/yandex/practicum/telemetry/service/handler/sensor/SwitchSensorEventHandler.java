package ru.yandex.practicum.telemetry.service.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SwitchSensorProto;
import ru.yandex.practicum.kafka.telemetry.event.SwitchSensorAvro;
import ru.yandex.practicum.telemetry.kafka.KafkaClientProducer;
import ru.yandex.practicum.telemetry.sensor.model.SensorEvent;
import ru.yandex.practicum.telemetry.sensor.model.SwitchSensorEvent;
import ru.yandex.practicum.telemetry.sensor.type.SensorEventType;

/**
 * Обработчик событий датчика переключения.
 * <p>
 * Данный класс реализует логику обработки событий датчика переключения.
 * Он преобразует событие в формат Avro и отправляет его в Kafka.
 *
 * Основные характеристики:
 * <ul>
 *     <li>Наследуется от {@code BaseSensorGRPCEventHandler} с типом записи {@code SwitchSensorAvro}</li>
 *     <li>Обрабатывает события датчика переключения</li>
 *     <li>Преобразует данные о состоянии переключателя</li>
 * </ul>
 *
 * Компоненты класса:
 * <ul>
 *     <li>Конструктор принимает {@code KafkaClientProducer} для отправки сообщений</li>
 *     <li>Метод {@code mapToAvro()} преобразует SensorEvent в SwitchSensorAvro</li>
 *     <li>Метод {@code getMessageType()} возвращает тип обрабатываемого события</li>
 * </ul>
 */
@Component
public class SwitchSensorEventHandler extends BaseSensorEventHandler<SwitchSensorAvro> {
    public SwitchSensorEventHandler(KafkaClientProducer producer) {
        super(producer);
    }

    @Override
    protected SwitchSensorAvro mapToAvro(SensorEventProto event) {
        SwitchSensorProto switchSensorEvent = event.getSwitchSensorEvent();
        return SwitchSensorAvro.newBuilder()
                .setState(switchSensorEvent.getState())
                .build();
    }

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.SWITCH_SENSOR_EVENT;
    }
}
