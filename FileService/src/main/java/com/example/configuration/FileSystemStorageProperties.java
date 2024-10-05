package com.example.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("file-system-storage")
public class FileSystemStorageProperties {
    /**
     * Folder location for storing files
     */
    private String location = "upload-dir";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
