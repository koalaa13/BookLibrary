package com.example.controller;

import com.example.service.MoneyAccountService;
import com.example.service.SubscriptionTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankPrivateController {
    @Autowired
    private MoneyAccountService moneyAccountService;
    @Autowired
    private SubscriptionTransactionService transactionService;

    @PostMapping("/account/create")
    public boolean createAccount(@RequestBody String userId) {
        moneyAccountService.createAccount(userId);
        return true;
    }

    @PostMapping("/transaction/create/{id}")
    public boolean createTransaction(@PathVariable("id") String subscriptionId) {
        transactionService.createPendingTransaction(subscriptionId);
        return true;
    }
}
