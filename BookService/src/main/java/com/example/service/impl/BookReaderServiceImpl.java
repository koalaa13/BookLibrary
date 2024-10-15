package com.example.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.example.entity.BookInfo;
import com.example.feign.SubscriptionServiceClient;
import com.example.repository.BookInfoRepository;
import com.example.service.BookReaderService;
import dao.BookInfoReaderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.HttpUtil;

@Service
public class BookReaderServiceImpl implements BookReaderService {
    @Autowired
    private BookInfoRepository bookInfoRepository;
    @Autowired
    private SubscriptionServiceClient subscriptionServiceClient;

    @Override
    public boolean isAllBooksPublished(List<String> bookIds) {
        for (BookInfo bookInfo : bookInfoRepository.findAllById(bookIds)) {
            if (!bookInfo.isPublished()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<BookInfoReaderDao> getBoughtBooks(String userId) {
        List<BookInfoReaderDao> res = new ArrayList<>();
        bookInfoRepository.findAllById(subscriptionServiceClient.getAvailableBooksByUser(userId))
                .forEach(b -> {
                    String downloadUrl = HttpUtil.buildDownloadLink(b.getFileUUID(), false);
                    res.add(
                            new BookInfoReaderDao(
                                    b.getId(),
                                    b.getShortDescription(),
                                    b.getAuthor(),
                                    b.getTitle(),
                                    downloadUrl
                            )
                    );
                });
        return res;
    }
}
