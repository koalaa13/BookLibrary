package com.example.dao.filter.cond;

import com.example.dao.filter.FilterType;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, visible = true, property = "filterType", include =
        JsonTypeInfo.As.EXISTING_PROPERTY)
public abstract class FilterCond {
    public FilterType filterType;

    protected FilterCond(FilterType filterType) {
        this.filterType = filterType;
    }

    public abstract String buildSqlCond();
}
