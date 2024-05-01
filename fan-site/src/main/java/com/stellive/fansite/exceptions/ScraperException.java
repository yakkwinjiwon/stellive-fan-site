package com.stellive.fansite.exceptions;

/**
 * 뉴스를 가져오는 과정에서 발생하는 예외
 */
public class ScraperException extends RuntimeException{
    public ScraperException(String message) {
        super(message);
    }

    public ScraperException(Throwable cause) {
        super(cause);
    }

    public ScraperException(String message, Throwable cause) {
        super(message, cause);
    }
}
