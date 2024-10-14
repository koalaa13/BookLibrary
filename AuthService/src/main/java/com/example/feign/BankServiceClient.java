package com.example.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "BankService", url = "http://localhost:8093")
public interface BankServiceClient {
    @PostMapping("/account/create")
    boolean createAccount(@Param("userId") String userId);
}