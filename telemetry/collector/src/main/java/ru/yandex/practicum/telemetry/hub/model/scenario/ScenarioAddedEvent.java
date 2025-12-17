package ru.yandex.practicum.telemetry.hub.model.scenario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.telemetry.hub.model.HubEvent;
import ru.yandex.practicum.telemetry.hub.type.HubEventType;

import java.util.List;

/**
 * Событие, сигнализирующее о добавлении нового сценария в систему.
 * <p>
 * Данное событие содержит информацию о добавленном сценарии, включая его название,
 * условия срабатывания и действия, которые будут выполнены при активации сценария.
 * <p>
 * Структура события включает следующие компоненты:
 * <ul>
 *     <li>Название сценария (name) - уникальное имя сценария</li>
 *     <li>Условия (conditions) - список условий, при которых сценарий активируется</li>
 *     <li>Действия (actions) - список действий, выполняемых при срабатывании сценария</li>
 * </ul>
 */
@Getter
@Setter
@ToString(callSuper = true)
public class ScenarioAddedEvent extends HubEvent {

    @NotBlank
    @Size(min = 3)
    private String name;

    @NotEmpty
    private List<ScenarioCondition> conditions;
    @NotEmpty
    private List<DeviceAction> actions;

    @Override
    public HubEventType getType() {
        return HubEventType.SCENARIO_ADDED;
    }
}
