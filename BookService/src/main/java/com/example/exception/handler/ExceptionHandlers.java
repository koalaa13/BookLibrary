package com.example.exception.handler;

import com.example.exception.BadRequestSendToModerationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler({BadRequestSendToModerationException.class})
    public ResponseEntity<String> handleBadRequestSendToModerationException(RuntimeException ex) {
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }
}
