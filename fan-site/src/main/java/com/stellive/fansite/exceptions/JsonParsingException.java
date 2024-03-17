package com.stellive.fansite.exceptions;

public class JsonParsingException extends RuntimeException{

    public JsonParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonParsingException(Throwable cause) {
        super(cause);
    }
}
