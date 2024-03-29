package com.stellive.fansite.exceptions;

/**
 * 뉴스를 가져오는 과정에서 발생하는 예외
 */
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
