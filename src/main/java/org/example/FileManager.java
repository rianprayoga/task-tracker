package org.example;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class FileManager {
    private final String PATHNAME = "data.json";
    private final ObjectMapper objectMapper = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES);

    public Data open(){
        File file = new File(PATHNAME);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            JsonParser parser = objectMapper.createParser(file);
            String text = parser.getText();
            if (text == null){
                return Data.empty();
            }

            return objectMapper.readValue(parser, Data.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Data data){
        try {
            objectMapper.writeValue(new File(PATHNAME), data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
