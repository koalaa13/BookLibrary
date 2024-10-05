package com.example;

import com.example.exception.IncorrectAuthTokenException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@SpringBootApplication
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @ExceptionHandler({IncorrectAuthTokenException.class})
    public ResponseEntity<String> handleUserRegistrationException(RuntimeException ex) {
        return ResponseEntity
                .badRequest()
                .body("Incorrect jwt token: " + ex.getMessage());
    }
}
