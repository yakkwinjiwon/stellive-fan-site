package com.stellive.fansite.exceptions;

public class ResponseParsingException extends RuntimeException{

        public ResponseParsingException(String message, Throwable cause) {
            super(message, cause);
        }

        public ResponseParsingException(Throwable cause) {
            super(cause);
        }
}
