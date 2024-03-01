package com.stellive.fansite.client;

public class RestTemplateApiException extends RuntimeException{

    public RestTemplateApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestTemplateApiException(Throwable cause) {
        super(cause);
    }
}
