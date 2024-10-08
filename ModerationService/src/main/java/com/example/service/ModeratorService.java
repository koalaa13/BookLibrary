package com.example.service;

import java.util.List;

import com.example.dao.ModerationResultItem;
import com.example.dao.ModeratorAssignResponse;
import com.example.dao.SuccessModeratorAssignResponse;

public interface ModeratorService {
    ModeratorAssignResponse assign(String moderatorId);
    List<SuccessModeratorAssignResponse> getAssignedTo(String moderatorId);
    String submitModerationResult(String requestId, List<ModerationResultItem> resultItems);
}
