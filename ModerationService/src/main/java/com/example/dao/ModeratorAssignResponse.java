package com.example.dao;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, visible = true, property = "type", include =
        JsonTypeInfo.As.EXISTING_PROPERTY)
public abstract class ModeratorAssignResponse {
    public static enum Type {
        FAILED,
        SUCCESS
    }

    public Type type;

    protected ModeratorAssignResponse(Type type) {
        this.type = type;
    }
}
