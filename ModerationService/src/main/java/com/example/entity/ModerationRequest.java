package com.example.entity;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

import dao.BookInfoModerationDao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity(name = ModerationRequest.TABLE_NAME)
@Table(name = ModerationRequest.TABLE_NAME)
public class ModerationRequest {
    public static final String TABLE_NAME = "moderation_request";

    public static enum Status {
        WAITING_MODERATOR,
        TAKEN_TO_WORK,
        READY
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Instant createdAt;
    @Enumerated(EnumType.STRING)
    private Status status = Status.WAITING_MODERATOR;
    private String moderatorId;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "book_info", columnDefinition = "jsonb")
    private BookInfoModerationDao bookInfo;

    public ModerationRequest() {
    }

    public ModerationRequest(BookInfoModerationDao bookInfo, Instant createdAt) {
        this.bookInfo = bookInfo;
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ModerationRequest that = (ModerationRequest) o;
        return Objects.equals(id, that.id);
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getModeratorId() {
        return moderatorId;
    }

    public void setModeratorId(String moderatorId) {
        this.moderatorId = moderatorId;
    }

    public BookInfoModerationDao getBookInfo() {
        return bookInfo;
    }

    public void setBookInfo(BookInfoModerationDao bookInfo) {
        this.bookInfo = bookInfo;
    }
}
