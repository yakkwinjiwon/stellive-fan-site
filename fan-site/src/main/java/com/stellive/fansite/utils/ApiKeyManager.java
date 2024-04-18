package com.stellive.fansite.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ApiKeyManager {

    @Value("${youtube-api-key}")
    private String youtubeApiKey;
}
