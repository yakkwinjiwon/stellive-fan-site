package com.stellive.fansite.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<String> handleRestClientException(RestClientException e) {
        log.error("API call error=", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error calling external API");
    }

    @ExceptionHandler(JsonParsingException.class)
    public ResponseEntity<String> handleJsonParsingException(JsonParsingException e) {
        log.error("JSON parsing error=", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error parsing JSON");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception e) {
        log.error("Unexpected error=", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
}
