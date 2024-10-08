package com.example.entity;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

import dao.ModerationResultItem;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity(name = ModerationResult.TABLE_NAME)
@Table(name = ModerationResult.TABLE_NAME)
public class ModerationResult {
    public static final String TABLE_NAME = "moderation_response";

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "moderation_verdicts", columnDefinition = "jsonb")
    private List<ModerationResultItem> moderationResultItems;
    @OneToOne(optional = false)
    private ModerationRequest moderationRequest;
    private Instant createdAt;

    public ModerationResult() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ModerationResult that = (ModerationResult) o;
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

    public List<ModerationResultItem> getModerationVerdictItems() {
        return moderationResultItems;
    }

    public void setModerationVerdictItems(List<ModerationResultItem> moderationResultItems) {
        this.moderationResultItems = moderationResultItems;
    }

    public List<ModerationResultItem> getModerationResultItems() {
        return moderationResultItems;
    }

    public void setModerationResultItems(List<ModerationResultItem> moderationResultItems) {
        this.moderationResultItems = moderationResultItems;
    }

    public ModerationRequest getModerationRequest() {
        return moderationRequest;
    }

    public void setModerationRequest(ModerationRequest moderationRequest) {
        this.moderationRequest = moderationRequest;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
