package ru.yandex.practicum.analyzer.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.analyzer.model.Sensor;
import ru.yandex.practicum.analyzer.repository.SensorRepository;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

import java.util.Optional;

/**
 * Обработчик событий удаления устройств (сенсоров) из системы.
 * Удаление из базы данных.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DeviceRemovedHandler implements HubEventHandler {
    private final SensorRepository repository;

    @Override
    @Transactional
    public void handle(HubEventAvro event) {
        DeviceRemovedEventAvro removedEvent = (DeviceRemovedEventAvro) event.getPayload();
        String sensorId = removedEvent.getId();
        String hubId = event.getHubId();

        // Проверяем существование устройства перед удалением
        Optional<Sensor> existingSensor = repository.findByIdAndHubId(sensorId, hubId);

        if (existingSensor.isPresent()) {
            repository.deleteByIdAndHubId(sensorId, hubId);
            log.info("Устройство с id = {} успешно удалено из хаба с hub_id = {}", sensorId, hubId);
        } else {
            log.info("Устройство с id = {} не найдено в хабе с hub_id = {}, удаление пропущено", sensorId, hubId);
        }
    }

    @Override
    public String getPayloadType() {
        return DeviceRemovedEventAvro.class.getSimpleName();
    }
}
