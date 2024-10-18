package com.example.feign;

import java.util.List;

import dao.SubscriptionDao;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "SubscriptionService", url = "http://subscription-service")
public interface SubscriptionServiceClient {
    @PostMapping("/get/processing")
    List<SubscriptionDao> getSubscriptionsForProcessingTransaction(@RequestBody List<String> ids);
}
