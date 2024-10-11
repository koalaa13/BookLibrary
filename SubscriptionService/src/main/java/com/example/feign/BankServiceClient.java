package com.example.feign;

import java.math.BigInteger;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "BankService", url = "http://localhost:8093")
public interface BankServiceClient {
    @PostMapping("/add/{id}")
    boolean addMoney(@PathVariable("id") String userId, @Param("amount") BigInteger amount);
}
