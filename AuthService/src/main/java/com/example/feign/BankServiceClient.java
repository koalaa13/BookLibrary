package com.example.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "BankService", url = "http://localhost:8093")
public interface BankServiceClient {
    @PostMapping("/account/create")
    boolean createAccount(@RequestBody String userId);
}