package com.stellive.fansite.exceptions;

public class ExtractFieldsException extends RuntimeException{

    public ExtractFieldsException(String message) {
        super(message);
    }

    public ExtractFieldsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExtractFieldsException(Throwable cause) {
        super(cause);
    }
}
