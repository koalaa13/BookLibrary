package com.example.service;

public interface TokenService {
    boolean checkToken(String token);
    String getRoleFromToken(String token);
    String getClientFromToken(String token);
}
