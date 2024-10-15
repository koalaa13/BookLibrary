package com.example.service;

import java.util.List;

import dao.BookInfoReaderDao;

public interface BookReaderService {
    boolean isAllBooksPublished(List<String> bookIds);
    List<BookInfoReaderDao> getBoughtBooks(String userId);
}
