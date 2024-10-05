package com.example.service;

import com.example.entity.Role;

// TODO generate refresh token too
public interface TokenService {
    String generateToken(String clientId, Role role);
}
