package com.example.dao;

public class SuccessModeratorAssignResponse extends ModeratorAssignResponse {
    public SuccessModeratorAssignResponse() {
        super(ModeratorAssignResponse.Type.SUCCESS);
    }

    public String shortDescription;
    public String author;
    public String title;
    public String downloadUrl;
}
