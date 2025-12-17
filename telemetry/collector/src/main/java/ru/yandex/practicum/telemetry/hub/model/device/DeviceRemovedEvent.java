package ru.yandex.practicum.telemetry.hub.model.device;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.telemetry.hub.model.HubEvent;
import ru.yandex.practicum.telemetry.hub.type.HubEventType;


/**
 * Событие, сигнализирующее об удалении устройства из системы.
 * <p>
 * Данное событие содержит информацию об удаленном устройстве и наследуется от базового класса HubEvent.
 * <p>
 * Основные параметры события:
 * <blockquote><pre>
 *     // Идентификатор хаба, к которому относится событие
 *     String hubId = "hub-123";
 *
 *     // Временная метка события
 *     Instant timestamp = Instant.now();
 *
 *     // Уникальный идентификатор удаленного устройства
 *     String id = "device-456";
 *
 *     // Тип события хаба
 *     HubEventType eventType = HubEventType.DEVICE_REMOVED;
 * </pre></blockquote>
 */
@Getter
@Setter
@ToString(callSuper = true)
public class DeviceRemovedEvent extends HubEvent {

    @NotBlank
    private String id;

    @Override
    public HubEventType getType() {
        return HubEventType.DEVICE_REMOVED;
    }
}
