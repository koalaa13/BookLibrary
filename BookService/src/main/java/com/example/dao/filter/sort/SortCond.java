package com.example.dao.filter.sort;

import com.example.dao.filter.SortOrder;
import com.example.dao.filter.SortType;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, visible = true, property = "sortType", include =
        JsonTypeInfo.As.EXISTING_PROPERTY)
public abstract class SortCond {
    public SortType sortType;
    public SortOrder sortOrder;

    protected SortCond(SortType sortType) {
        this.sortType = sortType;
    }

    public String buildSqlCond() {
        return "ORDER BY " + getFieldName() + (sortOrder == null ? "" : sortOrder.order);
    }

    public abstract String getFieldName();
}
