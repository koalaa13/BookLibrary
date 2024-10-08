package com.example.entity;

import java.util.List;
import java.util.Objects;

import com.example.dao.ModerationResponseItem;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity(name = ModerationResponse.TABLE_NAME)
@Table(name = ModerationResponse.TABLE_NAME)
public class ModerationResponse {
    public static final String TABLE_NAME = "moderation_response";

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "moderation_verdicts", columnDefinition = "jsonb")
    private List<ModerationResponseItem> moderationResponseItems;
    @OneToOne(optional = false)
    private ModerationRequest moderationRequest;

    public ModerationResponse() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ModerationResponse that = (ModerationResponse) o;
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

    public List<ModerationResponseItem> getModerationVerdictItems() {
        return moderationResponseItems;
    }

    public void setModerationVerdictItems(List<ModerationResponseItem> moderationResponseItems) {
        this.moderationResponseItems = moderationResponseItems;
    }

    public List<ModerationResponseItem> getModerationResponseItems() {
        return moderationResponseItems;
    }

    public void setModerationResponseItems(List<ModerationResponseItem> moderationResponseItems) {
        this.moderationResponseItems = moderationResponseItems;
    }

    public ModerationRequest getModerationRequest() {
        return moderationRequest;
    }

    public void setModerationRequest(ModerationRequest moderationRequest) {
        this.moderationRequest = moderationRequest;
    }
}
