package com.example.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.stream.Stream;

import com.example.configuration.FileSystemStorageProperties;
import com.example.exception.StorageException;
import com.example.service.StorageService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemStorageService implements StorageService {
    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(FileSystemStorageProperties properties) {
        if (properties.getLocation().trim().isEmpty()) {
            throw new StorageException("File upload location can not be Empty.");
        }

        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage location", e);
        }
    }

    @Override
    public String store(MultipartFile file) {
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        String savedFilename = UUID.randomUUID().toString();
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + originalFilename);
            }
            if (originalFilename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + originalFilename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(
                        inputStream,
                        this.rootLocation.resolve(savedFilename),
                        StandardCopyOption.REPLACE_EXISTING
                );
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + originalFilename, e);
        }

        return savedFilename;
    }

    @Override
    public Stream<Path> loadAll() {
//        try {
//            return Files.walk(this.rootLocation, 1)
//                    .filter(path -> !path.equals(this.rootLocation))
//                    .map(this.rootLocation::relativize);
//        } catch (IOException e) {
//            throw new StorageException("Failed to read stored files", e);
//        }
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Path load(String UUID) {
        return rootLocation.resolve(UUID);
    }

    @Override
    public Resource loadAsResource(String UUID) throws MalformedURLException {
        return new UrlResource(load(UUID).toUri());
    }

    @Override
    public void deleteAll() {
//        FileSystemUtils.deleteRecursively(rootLocation.toFile());
        throw new RuntimeException("Not implemented");
    }
}
