package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerSendToModerationException extends RuntimeException {
    public InternalServerSendToModerationException(String message) {
        super(message);
    }

    public InternalServerSendToModerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
