package ru.yandex.practicum.telemetry.service.handler.sensor;

import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.telemetry.sensor.model.SensorEvent;
import ru.yandex.practicum.telemetry.sensor.type.SensorEventType;

public interface SensorEventHandler {
    SensorEventProto.PayloadCase getMessageType();
    void handle(SensorEventProto event);
}
