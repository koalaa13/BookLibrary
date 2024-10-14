package com.example.service;

import java.util.List;

public interface BookReaderService {
    boolean isAllBooksPublished(List<String> bookIds);
}
