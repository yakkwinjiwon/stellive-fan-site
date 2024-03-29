package com.stellive.fansite.exceptions;

/**
 * 2xx 이외의 응답을 받았을 때 발생하는 예외
 */
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
