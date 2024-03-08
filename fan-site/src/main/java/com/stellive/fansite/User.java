package com.stellive.fansite;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.ToString;

@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private String name;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }
}
