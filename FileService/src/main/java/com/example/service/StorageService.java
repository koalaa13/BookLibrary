package com.example.service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    void init();

    String store(MultipartFile file);

    Stream<Path> loadAll();

    Path load(String UUID);

    Resource loadAsResource(String UUID) throws MalformedURLException;

    void deleteAll();
}
