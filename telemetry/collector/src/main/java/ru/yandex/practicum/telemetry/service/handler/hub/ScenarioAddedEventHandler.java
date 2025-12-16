package ru.yandex.practicum.telemetry.service.handler.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.telemetry.hub.model.HubEvent;
import ru.yandex.practicum.telemetry.hub.model.scenario.DeviceAction;
import ru.yandex.practicum.telemetry.hub.model.scenario.ScenarioAddedEvent;
import ru.yandex.practicum.telemetry.hub.model.scenario.ScenarioCondition;
import ru.yandex.practicum.telemetry.hub.type.HubEventType;
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
    protected ScenarioAddedEventAvro mapToAvro(HubEvent event) {
        ScenarioAddedEvent scenarioAddedEvent = (ScenarioAddedEvent) event;
        return ScenarioAddedEventAvro.newBuilder()
                .setName(scenarioAddedEvent.getName())
                .setConditions(scenarioAddedEvent.getConditions().stream()
                        .map(this::mapToConditionAvro)
                        .toList())
                .setActions(scenarioAddedEvent.getActions().stream()
                        .map(this::mapToActionAvro)
                        .toList())
                .build();
    }

    private ScenarioConditionAvro mapToConditionAvro(ScenarioCondition scenarioCondition) {

        return ScenarioConditionAvro.newBuilder()
                .setSensorId(scenarioCondition.getSensorId())
                .setOperation(ConditionOperationAvro.valueOf(scenarioCondition.getOperation().name()))
                .setType(ConditionTypeAvro.valueOf(scenarioCondition.getType().name()))
                .setValue(scenarioCondition.getValue())
                .build();
    }

    private DeviceActionAvro mapToActionAvro(DeviceAction deviceAction) {
        ActionTypeAvro actionTypeAvro = switch (deviceAction.getType()) {
            case INVERSE -> ActionTypeAvro.INVERSE;
            case ACTIVATE -> ActionTypeAvro.ACTIVATE;
            case SET_VALUE -> ActionTypeAvro.SET_VALUE;
            case DEACTIVATE -> ActionTypeAvro.DEACTIVATE;
        };
        return DeviceActionAvro.newBuilder()
                .setSensorId(deviceAction.getSensorId())
                .setType(ActionTypeAvro.valueOf(deviceAction.getType().name()))
                .setValue(deviceAction.getValue())
                .build();
    }

    @Override
    public HubEventType getMessageType() {
        return HubEventType.SCENARIO_ADDED;
    }
}
