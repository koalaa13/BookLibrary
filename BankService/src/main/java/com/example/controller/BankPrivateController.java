package com.example.controller;

import java.math.BigInteger;

import com.example.service.MoneyAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankPrivateController {
    @Autowired
    private MoneyAccountService moneyAccountService;

    @PostMapping("/create")
    public boolean createAccount(@Param("userId") String userId) {
        moneyAccountService.createAccount(userId);
        return true;
    }

    @PostMapping("/add/{id}")
    public boolean addMoney(@PathVariable("id") String userId, @Param("amount") BigInteger amount) {
        moneyAccountService.addMoney(userId, amount);
        return true;
    }
}
