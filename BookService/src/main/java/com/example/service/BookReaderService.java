package com.example.service;

import java.util.List;

import com.example.dao.BookInfoDao;
import com.example.dao.filter.FilterSortState;
import dao.BookInfoReaderDao;

public interface BookReaderService {
    boolean isAllBooksPublished(List<String> bookIds);
    List<BookInfoReaderDao> getBoughtBooks(String userId);
    List<BookInfoDao> getBooks(FilterSortState state, Integer offset, Integer limit);
}
