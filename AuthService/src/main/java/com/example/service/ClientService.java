package com.example.service;

import com.example.entity.Role;

public interface ClientService {
    void register(String clientId, String clientSecret, Role role);
    void checkCredentials(String clientId, String clientSecret);
    Role getRole(String clientId);
}
