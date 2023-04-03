package com.example.customer.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

public class Utils {

    private static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
    }

    public static <T> T readFromFile(String path, Class<T> objectClass) throws IOException {
        File file = new ClassPathResource(path).getFile();
        return MAPPER.readValue(file, objectClass);
    }

    public static String mapToString(Object object) throws JsonProcessingException {
        return MAPPER.writeValueAsString(object);
    }

}
