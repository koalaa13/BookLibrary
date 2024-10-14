package com.example.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "SubscriptionService", url = "http://localhost:8081")
public interface SubscriptionServiceClient {
    @PostMapping("/buy")
    boolean buySubscription(@RequestBody List<String> bookIds);

    @PostMapping("/buy/{id}")
    boolean buyBook(@PathVariable("id") String bookId);
}
