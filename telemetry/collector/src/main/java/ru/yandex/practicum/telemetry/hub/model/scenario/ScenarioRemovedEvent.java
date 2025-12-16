package ru.yandex.practicum.telemetry.hub.model.scenario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.telemetry.hub.model.HubEvent;
import ru.yandex.practicum.telemetry.hub.type.HubEventType;

/**
 * Событие, сигнализирующее об удалении сценария из системы.
 * <p>
 * Данное событие содержит информацию об удаленном сценарии. Событие наследуется от базового класса HubEvent и включает все его поля.
 *
 * Основные компоненты события:
 * <ul>
 *     <li>{@code name} - название удаленного сценария. Должен быть не пустым и содержать минимум 3 символа.</li>
 * </ul>
 */
@Getter
@Setter
@ToString(callSuper = true)
public class ScenarioRemovedEvent extends HubEvent {
    @NotBlank
    @Size(min = 3)
    private String name;

    @Override
    public HubEventType getType() {
        return HubEventType.SCENARIO_REMOVED;
    }
}
