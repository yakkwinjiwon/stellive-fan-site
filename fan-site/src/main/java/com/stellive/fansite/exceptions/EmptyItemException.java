package com.stellive.fansite.exceptions;

/**
 * 파싱된 아이템이 비어있을 때 발생하는 예외
 */
public class EmptyItemException extends RuntimeException{

    public EmptyItemException(String message) {
        super(message);
    }

    public EmptyItemException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyItemException(Throwable cause) {
        super(cause);
    }
}
