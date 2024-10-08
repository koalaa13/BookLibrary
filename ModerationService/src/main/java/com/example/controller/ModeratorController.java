package com.example.controller;

import java.util.List;

import com.example.dao.ModerationResultItem;
import com.example.dao.ModeratorAssignResponse;
import com.example.dao.SuccessModeratorAssignResponse;
import com.example.service.ModeratorService;
import exception.EntityAccessPermissionDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.ContextHelper;

@RestController
@RequestMapping("/api/v1")
public class ModeratorController {
    @Autowired
    private ModeratorService moderatorService;

    @PostMapping(value = "/assign")
    public ModeratorAssignResponse assignModerationRequest() {
        ContextHelper.checkCurrentRole("MODERATOR");
        return moderatorService.assign(ContextHelper.getCurrentUser());
    }

    @GetMapping("/assignedTo/{userId}")
    public List<SuccessModeratorAssignResponse> getAllAssigned(@PathVariable("userId") String userId) {
        String currentUser = ContextHelper.getCurrentUser();
        if (!"ADMIN".equals(ContextHelper.getCurrentUserRole()) && !userId.equals(currentUser)) {
            throw new EntityAccessPermissionDeniedException("Have no permissions to get another user's books");
        }
        return moderatorService.getAssignedTo(userId);
    }

    @PostMapping("/submit/{requestId}")
    public String submitModerationResult(@PathVariable("requestId") String requestId,
                                         @RequestBody List<ModerationResultItem> resultItems) {
        ContextHelper.checkCurrentRole("MODERATOR");
        return moderatorService.submitModerationResult(requestId, resultItems);
    }
}
