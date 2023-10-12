package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import ru.otus.model.Measurement;

import javax.json.JsonObject;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName =  fileName;
    }

    @Override
    public List<Measurement> load() {
        // читает файл, парсит и возвращает результат
        List<Measurement> result;
        ObjectMapper objectMapper =new ObjectMapper();
        try (var resourceAsStream = ResourcesFileLoader.class.getClassLoader()
                .getResourceAsStream(fileName)){
            result  = objectMapper.readerForListOf(Measurement.class).readValue(resourceAsStream);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
        return result;
    }
}
