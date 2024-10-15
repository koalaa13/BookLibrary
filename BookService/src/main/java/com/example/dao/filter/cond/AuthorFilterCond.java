package com.example.dao.filter.cond;

import java.util.List;
import java.util.stream.Collectors;

import com.example.dao.filter.FilterType;

public class AuthorFilterCond extends FilterCond {
    public AuthorFilterCond() {
        super(FilterType.AUTHOR);
    }

    public List<String> values;

    @Override
    public String buildSqlCond() {
        if (values.isEmpty()) {
            return "";
        }
        if (values.size() == 1) {
            return "author = '" + values.get(0) + "'";
        }
        return "author IN (" + values.stream().map(v -> "'" + v + "'").collect(Collectors.joining(", ")) + ")";
    }
}
