package com.example.dao.filter;

public enum FilterSign {
    NOT_EQUAL("<>"),
    EQUAL("="),
    GREATER(">"),
    LESS("<");

    public final String character;

    FilterSign(String character) {
        this.character = character;
    }
}
