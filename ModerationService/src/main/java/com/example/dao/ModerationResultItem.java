package com.example.dao;

public class ModerationResultItem {
    public static enum Type {
        NOT_MATCHING_CONTENT,
        DUPLICATION,
        VIOLATION_OF_TERMS,
        INCORRECT_BOOK_FILE,
        OTHER
    }

    public static enum Subject {
        TEXT,
        AUTHOR,
        TITLE,
        SHORT_DESCRIPTION
    }

    public Type type;
    public Subject subject;
    public String explanation;
}
