package com.example.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "BankService", url = "http://localhost:8093")
public interface BankServiceClient {
    @PostMapping("/transaction/create/{id}")
    boolean createTransaction(@PathVariable("id") String subscriptionId);
}
