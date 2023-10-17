package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FileSerializer implements Serializer {

    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        // формирует результирующий json и сохраняет его в файл
        JsonMapper jsonMapper = new JsonMapper();
        try (var fileWriter = new FileWriter(fileName)) {
            jsonMapper.writeValue(fileWriter,data);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
