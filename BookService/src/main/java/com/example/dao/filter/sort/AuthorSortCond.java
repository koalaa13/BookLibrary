package com.example.dao.filter.sort;

import com.example.dao.filter.SortType;

public class AuthorSortCond extends SortCond {
    public AuthorSortCond() {
        super(SortType.AUTHOR);
    }

    @Override
    public String getFieldName() {
        return "author";
    }
}
