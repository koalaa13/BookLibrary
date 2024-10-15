package com.example.dao.filter.cond;

import com.example.dao.filter.FilterType;

public class TitleFilterCond extends FilterCond {
    public TitleFilterCond() {
        super(FilterType.TITLE);
    }

    public String value;

    @Override
    public String buildSqlCond() {
        return "title LIKE '% + " + value + "%'";
    }
}
