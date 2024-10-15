package com.example.dao.filter.sort;

import com.example.dao.filter.SortType;

public class TitleSortCond extends SortCond {
    public TitleSortCond() {
        super(SortType.TITLE);
    }

    @Override
    public String getFieldName() {
        return "title";
    }
}
