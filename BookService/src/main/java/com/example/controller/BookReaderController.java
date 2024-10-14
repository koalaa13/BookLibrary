package com.example.controller;

import java.util.List;

import com.example.feign.SubscriptionServiceClient;
import com.example.service.BookReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return subscriptionServiceClient.buySubscription(bookIds);
    }

    @PostMapping("/buy/{id}")
    public boolean buyBook(@PathVariable("id") String bookId) {
        if (!bookReaderService.isAllBooksPublished(List.of(bookId))) {
            return false;
        }
        return subscriptionServiceClient.buyBook(bookId);
    }
}
