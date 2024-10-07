package org.example.json;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonParser {

    public Json parse(String json) {
        // Initialize ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        Json json1 =null;
        try {
            json1 = objectMapper.readValue(json, Json.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json1;
    }
}
