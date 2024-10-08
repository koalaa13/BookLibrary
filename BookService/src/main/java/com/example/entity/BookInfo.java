package com.example.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity(name = BookInfo.TABLE_NAME)
@Table(name = BookInfo.TABLE_NAME)
public class BookInfo {
    public static final String TABLE_NAME = "book_info";

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "file_uuid")
    private String fileUUID;
    @Lob
    private String shortDescription;
    private String author;
    private String title;
    private String uploader;
    private boolean inModeration = false;
    private String moderationResultId;

    public BookInfo() {
    }

    public BookInfo(String shortDescription, String author, String title, String uploader) {
        this.shortDescription = shortDescription;
        this.author = author;
        this.title = title;
        this.uploader = uploader;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BookInfo bookInfo = (BookInfo) o;
        return Objects.equals(id, bookInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileUUID() {
        return fileUUID;
    }

    public void setFileUUID(String fileUUID) {
        this.fileUUID = fileUUID;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public boolean isInModeration() {
        return inModeration;
    }

    public void setInModeration(boolean inModeration) {
        this.inModeration = inModeration;
    }

    public String getModerationResultId() {
        return moderationResultId;
    }

    public void setModerationResultId(String moderationResultId) {
        this.moderationResultId = moderationResultId;
    }
}
