package com.stellive.fansite.service.youtube;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stellive.fansite.exceptions.ApiResponseException;
import com.stellive.fansite.exceptions.ResponseParsingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * API 응답을 DTO로 매핑하는 클래스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ResponseMapper {

    private final ObjectMapper objectMapper;

    public <T> T mapToDto(ResponseEntity<String> response, Class<T> clazz) {


        if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
            try {
                return objectMapper.readValue(response.getBody(), clazz);
            } catch (JsonProcessingException e) {
                log.error("Failed to parse response body: {}", response.getBody());
                throw new ResponseParsingException(e);
            }
        } else {
            log.error("Unsuccessful API response: status code: {}, body: {}", response.getStatusCode(), response.getBody());
            throw new ApiResponseException("Unsuccessful API response");
        }
    }
}
