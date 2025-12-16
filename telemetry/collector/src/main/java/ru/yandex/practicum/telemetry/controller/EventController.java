package ru.yandex.practicum.telemetry.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.telemetry.hub.model.HubEvent;
import ru.yandex.practicum.telemetry.hub.type.HubEventType;
import ru.yandex.practicum.telemetry.sensor.model.SensorEvent;
import ru.yandex.practicum.telemetry.sensor.type.SensorEventType;
import ru.yandex.practicum.telemetry.service.handler.hub.HubEventHandler;
import ru.yandex.practicum.telemetry.service.handler.sensor.SensorEventHandler;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RestController
@RequestMapping("/events")
public class EventController {
    private final Map<SensorEventType, SensorEventHandler> sensorEventHandlers;
    private final Map<HubEventType, HubEventHandler> hubEventHandlers;

    public EventController(List<SensorEventHandler> sensorEventHandlerList, List<HubEventHandler> hubEventHandlerList) {
        this.sensorEventHandlers = sensorEventHandlerList.stream()
                .collect(Collectors.toMap(SensorEventHandler::getMessageType, Function.identity()));
        this.hubEventHandlers = hubEventHandlerList.stream()
                .collect(Collectors.toMap(HubEventHandler::getMessageType, Function.identity()));
    }

    @PostMapping("/sensors")
    public void collectSensorEvent(@Valid @RequestBody SensorEvent request) {
        log.info("Поступил запрос Sensor с телом = {}", request);
        if (sensorEventHandlers.containsKey(request.getType())) {
            sensorEventHandlers.get(request.getType()).handle(request);
        } else {
            throw new IllegalArgumentException("Такого события нет " + request.getType());
        }
        log.info("Выполнен запрос Sensor");
    }

    @PostMapping("/hubs")
    public void collectHubEvent(@Valid @RequestBody HubEvent request) {
        log.info("Поступил запрос Hub с телом = {}", request);
        if (hubEventHandlers.containsKey(request.getType())) {
            hubEventHandlers.get(request.getType()).handle(request);
        } else {
            throw new IllegalArgumentException("Такого события нет" + request.getType());
        }
        log.info("Выполнен запрос Hub");
    }

}
