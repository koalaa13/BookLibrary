package com.example.controller;

import com.example.dao.ErrorResponse;
import com.example.dao.TokenResponse;
import com.example.dao.UserCredentials;
import com.example.entity.Role;
import com.example.exception.LoginException;
import com.example.exception.RegistrationException;
import com.example.service.ClientService;
import com.example.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final ClientService clientService;
    private final TokenService tokenService;

    @Autowired
    public AuthController(ClientService clientService, TokenService tokenService) {
        this.clientService = clientService;
        this.tokenService = tokenService;
    }

    @PostMapping("/user")
    public ResponseEntity<String> registerUser(@RequestBody UserCredentials userCredentials) {
        clientService.register(userCredentials.clientId, userCredentials.clientSecret, Role.COMMON_USER);
        return ResponseEntity.ok("Registered");
    }

    @PostMapping("/moderator")
    public ResponseEntity<String> registerModerator(@RequestBody UserCredentials userCredentials) {
        clientService.register(userCredentials.clientId, userCredentials.clientSecret, Role.MODERATOR);
        return ResponseEntity.ok("Registered");
    }

    @PostMapping("/admin")
    public ResponseEntity<String> registerAdmin(@RequestBody UserCredentials userCredentials) {
        clientService.register(userCredentials.clientId, userCredentials.clientSecret, Role.ADMIN);
        return ResponseEntity.ok("Registered");
    }

    @PostMapping("/token")
    public TokenResponse getToken(@RequestBody UserCredentials userCredentials) {
        clientService.checkCredentials(userCredentials.clientId, userCredentials.clientSecret);
        return new TokenResponse(
                tokenService.generateToken(
                        userCredentials.clientId,
                        clientService.getRole(userCredentials.clientId)
                )
        );
    }

    @ExceptionHandler({RegistrationException.class, LoginException.class})
    public ResponseEntity<ErrorResponse> handleUserRegistrationException(RuntimeException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(ex.getMessage()));
    }
}
