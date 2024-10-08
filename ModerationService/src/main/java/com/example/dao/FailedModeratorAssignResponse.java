package com.example.dao;

public class FailedModeratorAssignResponse extends ModeratorAssignResponse {
    public FailedModeratorAssignResponse(String message) {
        super(Type.FAILED);
        this.message = message;
    }

    public String message;
}
