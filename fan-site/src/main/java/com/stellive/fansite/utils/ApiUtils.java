package com.stellive.fansite.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stellive.fansite.exceptions.ApiResponseException;
import com.stellive.fansite.exceptions.ResponseParsingException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Getter
@Component
@RequiredArgsConstructor
@Slf4j
public class ApiUtils {

    private final ObjectMapper objectMapper;

    /**
     * Youtube token
     */
    @Value("${youtube-api-key}")
    private String youtubeApiKey;

    public <T> T parseResponse(ResponseEntity<String> response, Class<T> clazz) {
        if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
            try {
                return objectMapper.readValue(response.getBody(), clazz);
            } catch (JsonProcessingException e) {
                throw new ResponseParsingException(e);
            }
        } else {
            log.error("YouTube API responded with status code: {} and body: {}",
                    response.getStatusCode(), response.getBody());
            throw new ApiResponseException("Unsuccessful API response");
        }
    }
}