package com.example.dao;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, visible = true, property = "message", include =
        JsonTypeInfo.As.EXISTING_PROPERTY)
public abstract class ModerationResultResponse {
    public String message;

    protected ModerationResultResponse(String message) {
        this.message = message;
    }
}
