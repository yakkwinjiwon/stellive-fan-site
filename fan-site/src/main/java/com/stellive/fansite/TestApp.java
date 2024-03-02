package com.stellive.fansite;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
public class TestApp {

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        String json = "{"
                + "\"name\": \"John Doe\","
                + "\"age\": 30" +
                "}";
        try {
            User user = mapper.readValue(json, User.class);
            System.out.println("user = " + user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
