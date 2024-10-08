package com.example.exception;

public class BadRequestSendToModerationException extends RuntimeException {
    public BadRequestSendToModerationException(String message) {
        super(message);
    }

    public BadRequestSendToModerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
