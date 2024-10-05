package com.example.service.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.entity.Role;
import com.example.service.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import util.Constants;

@Service
public class DefaultTokenService implements TokenService {
    @Value("${auth.jwt.secret}")
    private String secretKey;

    @Override
    public String generateToken(String clientId, Role role) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        // TODO move ttl of jwt in property
        Instant now = Instant.now();
        Instant exp = now.plus(30, ChronoUnit.MINUTES);

        return JWT.create()
                .withIssuer("auth-service")
                .withAudience("bookstore")
                .withSubject(clientId)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(exp))
                .withPayload(Map.of(Constants.JWT_ROLE_PAYLOAD_HEADER, role.toString()))
                .sign(algorithm);
    }
}
