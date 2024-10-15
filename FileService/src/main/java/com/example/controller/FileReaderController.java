package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reader")
public class FileReaderController {
    @Autowired
    private FilePrivateController filePrivateController;

    @GetMapping(value = "/download/{id}")
    public ResponseEntity<Resource> downloadFileForModeration(@PathVariable("id") String fileUUID) {
        // TODO check did reader buy this book
        return filePrivateController.downloadFile(fileUUID);
    }
}
