package com.example.dao;

import java.util.List;

import dao.ModerationResultItem;

public class ModerationResultWithErrorsResponse extends ModerationResultResponse {
    public ModerationResultWithErrorsResponse(String message, List<ModerationResultItem> errors) {
        super(message);
        this.errors = errors;
    }

    public List<ModerationResultItem> errors;
}
