package com.stellive.fansite.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stellive.fansite.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
public class TestApp {

    public static void main(String[] args) {
        Item item = new Item();
        Member member = new Member(item);
        item.setMember(member);
        System.out.println("member = " + member);

    }
}
