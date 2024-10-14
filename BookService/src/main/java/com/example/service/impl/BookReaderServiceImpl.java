package com.example.service.impl;

import java.util.List;

import com.example.entity.BookInfo;
import com.example.repository.BookInfoRepository;
import com.example.service.BookReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookReaderServiceImpl implements BookReaderService {
    @Autowired
    private BookInfoRepository bookInfoRepository;

    @Override
    public boolean isAllBooksPublished(List<String> bookIds) {
        for (BookInfo bookInfo : bookInfoRepository.findAllById(bookIds)) {
            if (!bookInfo.isPublished()) {
                return false;
            }
        }
        return true;
    }
}
