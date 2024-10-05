package com.example.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.exception.IncorrectAuthTokenException;
import com.example.service.TokenService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import util.Constants;

@Service
public class DefaultTokenService implements TokenService {
    @Value("${auth.jwt.secret}")
    private String secretKey;
    private JWTVerifier verifier;

    @PostConstruct
    private void postConstruct() {
        this.verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();
    }

    @Override
    public boolean checkToken(String token) {
        if (token == null) {
            return false;
        }

        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            if (!decodedJWT.getIssuer().equals("auth-service")) {
                return false;
            }
            if (!decodedJWT.getAudience().contains("bookstore")) {
                return false;
            }
        } catch (JWTVerificationException e) {
            return false;
        }

        return true;
    }

    @Override
    public String getRoleFromToken(String token) {
        if (token == null) {
            throw new IncorrectAuthTokenException("Token is null");
        }
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            String role = decodedJWT.getClaim(Constants.JWT_ROLE_PAYLOAD_HEADER).asString();
            if (role == null) {
                throw new IncorrectAuthTokenException("Null user role in token payload");
            }
            return role;
        } catch (JWTVerificationException e) {
            throw new IncorrectAuthTokenException("Error verify token", e);
        }
    }

    @Override
    public String getClientFromToken(String token) {
        if (token == null) {
            throw new IncorrectAuthTokenException("Token is null");
        }

        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            if (decodedJWT.getSubject() == null) {
                throw new IncorrectAuthTokenException("Null subject in token");
            }
            return decodedJWT.getSubject();
        } catch (JWTVerificationException e) {
            throw new IncorrectAuthTokenException("Error verify token", e);
        }
    }
}
