package com.example.dao.filter.cond;

import com.example.dao.filter.FilterSign;
import com.example.dao.filter.FilterType;

public class CreationDateFilterCond extends FilterCond {
    public CreationDateFilterCond() {
        super(FilterType.CREATION_DATE);
    }

    public FilterSign sign;
    public String value;

    @Override
    public String buildSqlCond() {
        return "created_at " + sign.character + "'" + value + "'";
    }
}
