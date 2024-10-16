package com.example.dao.filter.cond;

import com.example.dao.filter.FilterType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, visible = true, property = "filterType", include =
        JsonTypeInfo.As.EXISTING_PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TitleFilterCond.class, name = "TITLE"),
        @JsonSubTypes.Type(value = AuthorFilterCond.class, name = "AUTHOR"),
        @JsonSubTypes.Type(value = CreationDateFilterCond.class, name = "CREATION_DATE"),
        @JsonSubTypes.Type(value = UploaderFilterCond.class, name = "UPLOADER")
})
public abstract class FilterCond {
    public FilterType filterType;

    protected FilterCond(FilterType filterType) {
        this.filterType = filterType;
    }

    public abstract String buildSqlCond();
}
