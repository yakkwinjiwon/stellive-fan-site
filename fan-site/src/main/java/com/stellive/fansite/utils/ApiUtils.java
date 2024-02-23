package com.stellive.fansite.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class ApiUtils {

    @Value("${youtube-api-key}")
    private String youtubeApiKey;

    public String getYoutubeApiKey() {
        return youtubeApiKey;
    }

    public HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + gptApiKey);
//        headers.set(ApiConst.OPENAI, ApiConst.ASSISTANTS);
        return headers;
    }
}