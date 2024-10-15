package com.example.dao.filter.cond;

import java.util.List;
import java.util.stream.Collectors;

import com.example.dao.filter.FilterType;

public class UploaderFilterCond extends FilterCond {
    public UploaderFilterCond() {
        super(FilterType.UPLOADER);
    }

    public List<String> values;

    @Override
    public String buildSqlCond() {
        if (values.isEmpty()) {
            return "";
        }
        if (values.size() == 1) {
            return "uploader = " + values.get(0);
        }
        return "uploader IN (" + values.stream().map(v -> "'" + v + "'").collect(Collectors.joining(", ")) + ")";
    }
}
