package ru.yandex.practicum.telemetry.service.handler.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.DeviceRemovedEventProto;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;
import ru.yandex.practicum.telemetry.hub.model.HubEvent;
import ru.yandex.practicum.telemetry.hub.model.device.DeviceRemovedEvent;
import ru.yandex.practicum.telemetry.hub.type.HubEventType;
import ru.yandex.practicum.telemetry.kafka.KafkaClientProducer;

/**
 * Обработчик событий удаления устройства.
 * <p>
 * Данный класс реализует логику обработки событий удаления устройств из системы.
 * Он преобразует событие удаления устройства в формат Avro и отправляет его в Kafka.
 *
 * Основные характеристики:
 * <ul>
 *     <li>Наследуется от {@code BaseHubEventHandler} с типом записи {@code DeviceRemovedEventAvro}</li>
 *     <li>Реализует преобразование события в Avro формат</li>
 *     <li>Обрабатывает события типа DEVICE_REMOVED</li>
 * </ul>
 *
 * Компоненты класса:
 * <ul>
 *     <li>Конструктор принимает {@code KafkaClientProducer} для отправки сообщений</li>
 *     <li>Метод {@code mapToAvro()} преобразует HubEvent в DeviceRemovedEventAvro</li>
 *     <li>Метод {@code getMessageType()} возвращает тип обрабатываемого события</li>
 * </ul>
 */
@Component
public class DeviceRemovedEventHandler extends BaseHubEventHandler<DeviceRemovedEventAvro> {
    public DeviceRemovedEventHandler(KafkaClientProducer producer) {
        super(producer);
    }

    @Override
    protected DeviceRemovedEventAvro mapToAvro(HubEventProto event) {
        DeviceRemovedEventProto deviceRemovedEvent = event.getDeviceRemoved();

        return DeviceRemovedEventAvro.newBuilder()
                .setId(deviceRemovedEvent.getId())
                .build();
    }

    @Override
    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.DEVICE_REMOVED;
    }
}
