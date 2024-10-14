package com.example.controller;

import java.math.BigDecimal;
import java.util.List;

import com.example.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping("/price")
    public BigDecimal getSubscriptionPriceByBooks(@RequestBody List<String> bookIds) {
        return subscriptionService.getSubscriptionPriceByBooks(bookIds);
    }
}
