package com.example.controller;

import java.util.List;

import com.example.dao.BookInfoDao;
import com.example.dao.SearchDao;
import com.example.feign.SubscriptionServiceClient;
import com.example.service.BookReaderService;
import dao.BookInfoReaderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.ContextHelper;

@RestController
@RequestMapping("/api/v1")
public class BookReaderController {
    @Autowired
    private SubscriptionServiceClient subscriptionServiceClient;
    @Autowired
    private BookReaderService bookReaderService;

    @PostMapping("/buy")
    public boolean buySubscription(@RequestBody List<String> bookIds) {
        if (!bookReaderService.isAllBooksPublished(bookIds)) {
            return false;
        }
        return subscriptionServiceClient.buySubscription(ContextHelper.getCurrentUser(), bookIds);
    }

    @PostMapping("/buy/{id}")
    public boolean buyBook(@PathVariable("id") String bookId) {
        if (!bookReaderService.isAllBooksPublished(List.of(bookId))) {
            return false;
        }
        return subscriptionServiceClient.buyBook(ContextHelper.getCurrentUser(), bookId);
    }

    @GetMapping("/bought/{id}")
    public List<BookInfoReaderDao> getBoughtBooks(@PathVariable("id") String userId) {
        ContextHelper.checkEntityAccess(
                userId,
                "Have no permissions to get another user's bought books"
        );
        return bookReaderService.getBoughtBooks(userId);
    }

    @GetMapping("/search")
    public List<BookInfoDao> searchBooks(@RequestBody SearchDao searchDao) {
        return bookReaderService.getBooks(
                searchDao.state,
                searchDao.offset,
                searchDao.limit
        );
    }
}
