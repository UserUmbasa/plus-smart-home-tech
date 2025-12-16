package ru.yandex.practicum.telemetry.kafka;

import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
/**
 * Сериализатор для преобразования Avro записей в байтовый массив.
 * <p>
 * Данный класс реализует логику сериализации объектов SpecificRecordBase в байтовый формат
 * с использованием Avro библиотеки. Используется для работы с Kafka продюсером.
 *
 * Основные компоненты класса:
 * <ul>
 *     <li>{@code encoderFactory} - фабрика для создания кодировщиков Avro</li>
 *     <li>{@code encoder} - бинарный кодировщик для преобразования данных</li>
 * </ul>
 *
 * Функционал:
 * <ul>
 *     <li>Сериализация Avro записей в байтовый массив</li>
 *     <li>Обработка исключений при сериализации</li>
 *     <li>Работа с различными типами Avro записей</li>
 * </ul>
 */

public class GeneralAvroSerializer implements Serializer<SpecificRecordBase> {

    private final EncoderFactory encoderFactory = EncoderFactory.get();
    private BinaryEncoder encoder;

    public byte[] serialize(String topic, SpecificRecordBase data) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] result = null;
            encoder = encoderFactory.binaryEncoder(out, encoder);
            if (data != null) {
                DatumWriter<SpecificRecordBase> writer = new SpecificDatumWriter<>(data.getSchema());
                writer.write(data, encoder);
                encoder.flush();
                result = out.toByteArray();
            }
            return result;
        } catch (IOException ex) {
            throw new SerializationException("Ошибка сериализации данных для топика [" + topic + "]", ex);
        }
    }
}
