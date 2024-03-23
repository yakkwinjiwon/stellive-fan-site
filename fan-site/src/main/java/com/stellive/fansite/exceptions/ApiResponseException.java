package com.stellive.fansite.exceptions;

public class ApiResponseException extends RuntimeException{

    public ApiResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiResponseException(String message) {
        super(message);
    }

    public ApiResponseException(Throwable cause) {
        super(cause);
    }
}
