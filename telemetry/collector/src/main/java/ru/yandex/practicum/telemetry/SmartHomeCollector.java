package ru.yandex.practicum.telemetry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.yandex.practicum.telemetry.hub.type.DeviceType;

@SpringBootApplication
public class SmartHomeCollector {
    public static void main(String[] args) {
        SpringApplication.run(SmartHomeCollector.class, args);
        DeviceType jfj = DeviceType.CLIMATE_SENSOR;
    }
}
