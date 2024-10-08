package com.example.service;

import com.example.dao.ModeratorAssignResponse;

public interface ModeratorService {
    ModeratorAssignResponse assign(String moderatorId);
}
