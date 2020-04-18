package com.healthylife.restapi.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class TestKlasse {
    public static void main(String[] args) throws JsonProcessingException {
        //
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        TestObject to = new TestObject("heeej", 23);

        String json = mapper.writeValueAsString(to);
        System.out.println(json);



    }


}
