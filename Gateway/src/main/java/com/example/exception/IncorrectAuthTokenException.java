package com.example.exception;

public class IncorrectAuthTokenException extends RuntimeException {
    public IncorrectAuthTokenException(String message) {
        super(message);
    }

    public IncorrectAuthTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
