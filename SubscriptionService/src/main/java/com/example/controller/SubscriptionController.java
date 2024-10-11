package com.example.controller;

import java.math.BigInteger;
import java.util.List;

import com.example.service.SubscriptionService;
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
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping("/price")
    public BigInteger getSubscriptionPriceByBooks(@RequestBody List<String> bookIds) {
        return subscriptionService.getSubscriptionPriceByBooks(bookIds);
    }

    @PostMapping("/buy")
    public boolean buySubscription(@RequestBody List<String> bookIds) {
        return subscriptionService.createSubscription(ContextHelper.getCurrentUser(), bookIds, false);
    }

    @PostMapping("/buy/{id}")
    public boolean buyBook(@PathVariable("id") String bookId) {
        return subscriptionService.createSubscription(ContextHelper.getCurrentUser(), List.of(bookId), true);
    }
}
