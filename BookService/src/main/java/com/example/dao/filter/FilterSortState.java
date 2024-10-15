package com.example.dao.filter;

import java.util.List;

import com.example.dao.filter.cond.FilterCond;
import com.example.dao.filter.sort.SortCond;

public class FilterSortState {
    public SortCond sortCond;
    public List<FilterCond> filterConds;
}
