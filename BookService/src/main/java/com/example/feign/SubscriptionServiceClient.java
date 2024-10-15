package com.example.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "SubscriptionService", url = "http://localhost:8081")
public interface SubscriptionServiceClient {
    @PostMapping("/buy/{userId}")
    boolean buySubscription(@PathVariable("userId") String userId, @RequestBody List<String> bookIds);

    @PostMapping("/buy/{userId}/{id}")
    boolean buyBook(@PathVariable("userId") String userId, @PathVariable("id") String bookId);

    @GetMapping("/get/available/{userId}")
    List<String> getAvailableBooksByUser(@PathVariable("userId") String userId);
}
