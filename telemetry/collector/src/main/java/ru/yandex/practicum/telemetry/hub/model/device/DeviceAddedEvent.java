package ru.yandex.practicum.telemetry.hub.model.device;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.telemetry.hub.model.HubEvent;
import ru.yandex.practicum.telemetry.hub.type.DeviceType;
import ru.yandex.practicum.telemetry.hub.type.HubEventType;

/**
 * Событие, сигнализирующее о добавлении нового устройства в систему.
 * <p>
 * Данное событие содержит информацию о добавленном устройстве, включая его идентификатор
 * и тип. Событие наследуется от базового класса HubEvent и включает все его поля.
 * <p>
 * Основные параметры события:
 * <blockquote><pre>
 *     // Идентификатор хаба, к которому относится событие
 *     String hubId = "hub-123";
 *
 *     // Временная метка события
 *     Instant timestamp = Instant.now();
 *
 *     // Уникальный идентификатор добавленного устройства
 *     String id = "device-456";
 *
 *     // Тип добавленного устройства
 *     DeviceType deviceType = DeviceType.TEMPERATURE_SENSOR;
 *
 *     // Тип события хаба
 *     HubEventType eventType = HubEventType.DEVICE_ADDED;
 * </pre></blockquote>
 */

@Getter
@Setter
@ToString(callSuper = true)
public class DeviceAddedEvent extends HubEvent {

    @NotBlank
    private String id;

    @NotNull
    private DeviceType deviceType;

    @Override
    public HubEventType getType() {
        return HubEventType.DEVICE_ADDED;
    }
}
