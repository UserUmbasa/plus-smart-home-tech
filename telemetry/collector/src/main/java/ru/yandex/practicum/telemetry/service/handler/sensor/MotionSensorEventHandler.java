package ru.yandex.practicum.telemetry.service.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.MotionSensorProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.MotionSensorAvro;
import ru.yandex.practicum.telemetry.kafka.KafkaClientProducer;
import ru.yandex.practicum.telemetry.sensor.model.MotionSensorEvent;
import ru.yandex.practicum.telemetry.sensor.model.SensorEvent;
import ru.yandex.practicum.telemetry.sensor.type.SensorEventType;

/**
 * Обработчик событий датчика движения.
 * <p>
 * Данный класс реализует логику обработки событий датчика движения.
 * Он преобразует событие в формат Avro и отправляет его в Kafka.
 *
 * Основные характеристики:
 * <ul>
 *     <li>Наследуется от {@code BaseSensorEventHandler} с типом записи {@code MotionSensorAvro}</li>
 *     <li>Обрабатывает события датчика движения</li>
 *     <li>Преобразует данные о качестве связи, напряжении и обнаружении движения</li>
 * </ul>
 *
 * Компоненты класса:
 * <ul>
 *     <li>Конструктор принимает {@code KafkaClientProducer} для отправки сообщений</li>
 *     <li>Метод {@code mapToAvro()} преобразует SensorEvent в MotionSensorAvro</li>
 *     <li>Метод {@code getMessageType()} возвращает тип обрабатываемого события</li>
 * </ul>
 */
@Component
public class MotionSensorEventHandler extends BaseSensorEventHandler<MotionSensorAvro> {
    public MotionSensorEventHandler(KafkaClientProducer producer) {
        super(producer);
    }

    @Override
    protected MotionSensorAvro mapToAvro(SensorEventProto event) {
        MotionSensorProto motionSensorEvent = event.getMotionSensorEvent();
        return MotionSensorAvro.newBuilder()
                .setLinkQuality(motionSensorEvent.getLinkQuality())
                .setVoltage(motionSensorEvent.getVoltage())
                .setMotion(motionSensorEvent.getMotion())
                .build();
    }

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.MOTION_SENSOR_EVENT;
    }
}