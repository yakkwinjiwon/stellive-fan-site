package com.stellive.fansite.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ApiUtils {
    /**
     * Youtube token
     */
    @Value("${youtube-api-key}")
    private String youtubeApiKey;

    /**
     * Twitter token
     */
    @Value("${twitter-api-key}")
    private String twitterApiKey;

    @Value("${twitter-api-key-secret}")
    private String twitterApiKeySecret;

    @Value("${twitter-bearer-token}")
    private String twitterBearerToken;

    @Value("${twitter-client-id}")
    private String twitterClientId;

    @Value("${twitter-client-secret}")
    private String twitterClientSecret;

    @Value("${twitter-access-token}")
    private String twitterAccessToken;

    @Value("${twitter-access-token-secret}")
    private String twitterAccessTokenSecret;
}