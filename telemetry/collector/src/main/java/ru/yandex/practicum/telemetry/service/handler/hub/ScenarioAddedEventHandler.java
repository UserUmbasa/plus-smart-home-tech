package ru.yandex.practicum.telemetry.service.handler.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionProto;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioAddedEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioConditionProto;
import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.telemetry.kafka.KafkaClientProducer;

/**
 * Обработчик событий добавления сценария.
 * <p>
 * Данный класс реализует логику обработки событий добавления новых сценариев в систему.
 * Он преобразует событие добавления сценария в формат Avro, включая все условия и действия,
 * и отправляет его в Kafka.
 *
 * Основные характеристики:
 * <ul>
 *     <li>Наследуется от {@code BaseHubEventHandler} с типом записи {@code ScenarioAddedEventAvro}</li>
 *     <li>Реализует преобразование события в Avro формат</li>
 *     <li>Обрабатывает события типа SCENARIO_ADDED</li>
 * </ul>
 *
 * Компоненты класса:
 * <ul>
 *     <li>Конструктор принимает {@code KafkaClientProducer} для отправки сообщений</li>
 *     <li>Метод {@code mapToAvro()} преобразует HubEvent в ScenarioAddedEventAvro</li>
 *     <li>Вспомогательные методы для маппинга условий и действий</li>
 *     <li>Метод {@code getMessageType()} возвращает тип обрабатываемого события</li>
 * </ul>
 */
@Component
public class ScenarioAddedEventHandler extends BaseHubEventHandler<ScenarioAddedEventAvro> {
    public ScenarioAddedEventHandler(KafkaClientProducer producer) {
        super(producer);
    }

    @Override
    protected ScenarioAddedEventAvro mapToAvro(HubEventProto event) {
        ScenarioAddedEventProto scenarioAddedEvent = event.getScenarioAdded();
        return ScenarioAddedEventAvro.newBuilder()
                .setName(scenarioAddedEvent.getName())
                .setConditions(scenarioAddedEvent.getConditionList().stream()
                        .map(this::mapToConditionAvro)
                        .toList())
                .setActions(scenarioAddedEvent.getActionList().stream()
                        .map(this::mapToActionAvro)
                        .toList())
                .build();
    }

    private ScenarioConditionAvro mapToConditionAvro(ScenarioConditionProto scenarioCondition) {
        Object value = switch (scenarioCondition.getValueCase()) {
            case BOOL_VALUE -> scenarioCondition.getBoolValue();
            case INT_VALUE -> scenarioCondition.getIntValue();
            case VALUE_NOT_SET -> throw new IllegalArgumentException("Condition. Value not set.");
        };

        return ScenarioConditionAvro.newBuilder()
                .setSensorId(scenarioCondition.getSensorId())
                .setOperation(ConditionOperationAvro.valueOf(scenarioCondition.getOperation().name()))
                .setType(ConditionTypeAvro.valueOf(scenarioCondition.getType().name()))
                .setValue(value)
                .build();
    }

    private DeviceActionAvro mapToActionAvro(DeviceActionProto deviceAction) {
        return DeviceActionAvro.newBuilder()
                .setSensorId(deviceAction.getSensorId())
                .setType(ActionTypeAvro.valueOf(deviceAction.getType().name()))
                .setValue(deviceAction.getValue())
                .build();
    }

    @Override
    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.SCENARIO_ADDED;
    }
}
