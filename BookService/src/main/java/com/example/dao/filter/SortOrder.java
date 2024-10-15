package com.example.dao.filter;

public enum SortOrder {
    ASC("ASC"),
    DESC("DESC");

    public final String order;

    SortOrder(String order) {
        this.order = order;
    }
}
