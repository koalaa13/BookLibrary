package com.example.controller;

import java.util.List;

import com.example.service.SubscriptionService;
import dao.SubscriptionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import util.ContextHelper;

@RestController
public class SubscriptionPrivateController {
    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/buy/{userId}")
    public boolean buySubscription(@PathVariable("userId") String userId, @RequestBody List<String> bookIds) {
        return subscriptionService.createSubscription(userId, bookIds, false);
    }

    @PostMapping("/buy/{userId}/{id}")
    public boolean buyBook(@PathVariable("userId") String userId, @PathVariable("id") String bookId) {
        return subscriptionService.createSubscription(userId, List.of(bookId), true);
    }

    @PostMapping("/get/processing")
    public List<SubscriptionDao> getSubscriptionsForProcessingTransaction(@RequestBody List<String> ids) {
        return subscriptionService.getSubscriptions(ids);
    }

    @GetMapping("/get/available/{userId}")
    public List<String> getAvailableBooksByUser(@PathVariable("userId") String userId) {
        return subscriptionService.getBooksByUser(userId);
    }
}
