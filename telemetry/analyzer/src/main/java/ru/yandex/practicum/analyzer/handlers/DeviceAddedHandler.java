package ru.yandex.practicum.analyzer.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.analyzer.model.Sensor;
import ru.yandex.practicum.analyzer.repository.SensorRepository;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

import java.util.Optional;

/**
 * Обработчик событий добавления устройств в систему.
 * Данный класс отвечает за обработку событий добавления новых устройств (сенсоров)
 * в систему и сохранение их в репозиторий.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DeviceAddedHandler implements HubEventHandler {
    private final SensorRepository repository;

    @Override
    @Transactional
    public void handle(HubEventAvro event) {
        DeviceAddedEventAvro deviceAddedEvent = (DeviceAddedEventAvro) event.getPayload();
        String sensorId = deviceAddedEvent.getId();
        String hubId = event.getHubId();
        // Проверяем, существует ли устройство уже в базе
        Optional<Sensor> existingSensor = repository.findById(sensorId);

        if (existingSensor.isPresent()) {
            log.info("Устройство с id={} уже существует для хаба {}", sensorId, hubId);
            return;
        }

        Sensor newSensor = Sensor.builder()
                .id(sensorId)
                .hubId(hubId)
                .build();

        repository.save(newSensor);
        log.info("Устройство с id={} успешно добавлено для хаба {}", sensorId, hubId);
    }

    @Override
    public String getPayloadType() {
        return DeviceAddedEventAvro.class.getSimpleName();
    }
}
