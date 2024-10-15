package com.example.dao.filter.sort;

import com.example.dao.filter.SortType;

public class UploaderSortCond extends SortCond {
    public  UploaderSortCond() {
        super(SortType.UPLOADER);
    }

    @Override
    public String getFieldName() {
        return "uploader";
    }
}
