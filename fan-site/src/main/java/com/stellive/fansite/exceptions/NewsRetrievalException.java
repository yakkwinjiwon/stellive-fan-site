package com.stellive.fansite.exceptions;

public class NewsRetrievalException extends RuntimeException{
    public NewsRetrievalException(String message) {
        super(message);
    }

    public NewsRetrievalException(Throwable cause) {
        super(cause);
    }

    public NewsRetrievalException(String message, Throwable cause) {
        super(message, cause);
    }
}
