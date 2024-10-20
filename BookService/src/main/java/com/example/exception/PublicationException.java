package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class PublicationException extends RuntimeException {
    public PublicationException(String message) {
        super(message);
    }
}
