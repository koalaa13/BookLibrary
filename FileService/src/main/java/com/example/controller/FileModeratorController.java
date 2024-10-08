package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.ContextHelper;

@RestController
@RequestMapping("/api/v1")
public class FileModeratorController {
    @Autowired
    private FilePrivateController filePrivateController;

    @GetMapping(value = "/download/{id}")
    public ResponseEntity<Resource> downloadFileForModeration(@PathVariable("id") String fileUUID) {
        ContextHelper.checkCurrentRole("MODERATOR");
        // TODO check is moderator assigned to moderate this book
        return filePrivateController.downloadFile(fileUUID);
    }
}
