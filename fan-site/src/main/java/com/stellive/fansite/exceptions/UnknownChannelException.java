package com.stellive.fansite.exceptions;

/**
 * 존재하지 않는 채널을 요청했을 때 발생하는 예외
 */
public class UnknownChannelException extends RuntimeException{

    public UnknownChannelException(String message) {
        super(message);
    }

    public UnknownChannelException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownChannelException(Throwable cause) {
        super(cause);
    }
}
