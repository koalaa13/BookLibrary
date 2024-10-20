package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ChangeInModerationStatusException extends RuntimeException {
    public ChangeInModerationStatusException(String message) {
        super(message);
    }
}
