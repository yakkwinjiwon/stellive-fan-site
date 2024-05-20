package com.stellive.fansite.exceptions.handler;

import com.stellive.fansite.exceptions.ApiResponseException;
import com.stellive.fansite.exceptions.EmptyItemException;
import com.stellive.fansite.exceptions.ResponseParsingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<String> handleRestClientException(RestClientException e) {
        log.error("Error calling API", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error calling API");
    }

    @ExceptionHandler(EmptyItemException.class)
    public ResponseEntity<String> handleEmptyItemException(EmptyItemException e) {
        log.error("Empty item error=", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Empty item error");
    }

    @ExceptionHandler(ApiResponseException.class)
    public ResponseEntity<String> handleApiResponseException(ApiResponseException e) {
        log.error("API response error=", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error in API response");
    }

    @ExceptionHandler(ResponseParsingException.class)
    public ResponseEntity<String> handleResponseParsingException(ResponseParsingException e) {
        log.error("Error parsing API response=", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error parsing API response");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception e) {
        log.error("Unexpected error=", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred");
    }
}
