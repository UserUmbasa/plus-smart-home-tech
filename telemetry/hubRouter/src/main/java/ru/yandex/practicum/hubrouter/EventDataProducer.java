package ru.yandex.practicum.hubrouter;

import com.google.protobuf.Timestamp;
import jakarta.validation.constraints.NotNull;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.collector.CollectorControllerGrpc;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.grpc.telemetry.event.TemperatureSensorProto;

import java.time.Instant;

@Component
public class EventDataProducer {
    @GrpcClient("collector")
    CollectorControllerGrpc.CollectorControllerBlockingStub collectorStub;
    private final Logger log = LoggerFactory.getLogger(getClass());

    private SensorEventProto createTemperatureSensorEvent(TemperatureSensor sensor) {
        int temperatureCelsius = getRandomSensorValue(sensor.getTemperatureC());
        int temperatureFahrenheit = (int) (temperatureCelsius * 1.8 + 32);
        Instant ts = Instant.now();


        return SensorEventProto.newBuilder()
                .setId(sensor.getId())
                .setTimestamp(Timestamp.newBuilder()
                        .setSeconds(ts.getEpochSecond())
                        .setNanos(ts.getNano())
                ).setTemperatureSensorEvent(
                        TemperatureSensorProto.newBuilder()
                                .setTemperatureC(temperatureCelsius)
                                .setTemperatureF(temperatureFahrenheit)
                                .build()
                )
                .build();
    }

    @Scheduled(initialDelay = 1000, fixedDelay = 1000)
    public void echo() {
        log.info("echo send");
        var ts = createTemperatureSensorEvent(new TemperatureSensor("123",15,30));
        // отправка сообщения с помощью готового к использованию метода
        var result = collectorStub.collectSensorEvent(ts);
        log.info("echo response received");
    }

    private int getRandomSensorValue(@NotNull int temperatureC) {
        return (int) (Math.random() * temperatureC);
    }
}
