package com.stellive.fansite.exceptions;

/**
 * 응답 파싱 과정에서 발생하는 예외
 */
public class ResponseParsingException extends RuntimeException{

        public ResponseParsingException(String message, Throwable cause) {
            super(message, cause);
        }

    public ResponseParsingException(String message) {
        super(message);
    }

    public ResponseParsingException(Throwable cause) {
            super(cause);
        }
}
