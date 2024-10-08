package com.example.controller;

import com.example.dao.ModeratorAssignResponse;
import com.example.service.ModeratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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

    // TODO add endpoint for getting all assigned

    // TODO add endpoint for submit moderation result

    // TODO send moderation result async via kafka back to bookInfo service
}
