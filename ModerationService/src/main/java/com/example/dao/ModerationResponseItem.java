package com.example.dao;

public class ModerationResponseItem {
    public static enum Type {
        NOT_MATCHING_CONTENT,
        DUPLICATION,
        VIOLATION_OF_TERMS,
        INCORRECT_BOOK_FILE,
        OTHER
    }

    public Type type;
    public String explanation;
}
