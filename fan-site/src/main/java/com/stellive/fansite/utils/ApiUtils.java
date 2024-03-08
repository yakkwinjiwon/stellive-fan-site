package com.stellive.fansite.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ApiUtils {

    @Value("${youtube-api-key}")
    private String youtubeApiKey;

}