package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ChangePublishedException extends RuntimeException {
    public ChangePublishedException(String message) {
        super(message);
    }
}
