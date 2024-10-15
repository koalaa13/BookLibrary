package com.example.dao.filter.sort;

import com.example.dao.filter.SortType;

public class CreationDateSortCond extends SortCond {
    public CreationDateSortCond() {
        super(SortType.CREATION_DATE);
    }

    @Override
    public String getFieldName() {
        return "created_at";
    }
}
