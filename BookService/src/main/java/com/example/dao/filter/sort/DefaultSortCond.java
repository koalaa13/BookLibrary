package com.example.dao.filter.sort;

import com.example.dao.filter.SortType;

public class DefaultSortCond extends SortCond {
    public DefaultSortCond() {
        super(SortType.DEFAULT);
    }

    @Override
    public String getFieldName() {
        return "id";
    }
}
