package com.example.controller;

import java.util.List;

import com.example.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import util.ContextHelper;

@RestController
public class SubscriptionPrivateController {
    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/buy")
    public boolean buySubscription(@RequestBody List<String> bookIds) {
        return subscriptionService.createSubscription(ContextHelper.getCurrentUser(), bookIds, false);
    }

    @PostMapping("/buy/{id}")
    public boolean buyBook(@PathVariable("id") String bookId) {
        return subscriptionService.createSubscription(ContextHelper.getCurrentUser(), List.of(bookId), true);
    }
}
