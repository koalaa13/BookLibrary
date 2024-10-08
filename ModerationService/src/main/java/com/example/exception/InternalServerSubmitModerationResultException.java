package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerSubmitModerationResultException extends RuntimeException {
    public InternalServerSubmitModerationResultException(String message) {
        super(message);
    }

    public InternalServerSubmitModerationResultException(String message, Throwable cause) {
        super(message, cause);
    }
}
