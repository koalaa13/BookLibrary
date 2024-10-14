package com.example.controller;

import java.util.List;

import com.example.service.BookPriceService;
import com.example.service.MoneyAccountService;
import com.example.service.SubscriptionTransactionService;
import dao.BookInfoPriceDao;
import dao.BookInfoPriceUploaderDao;
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
    @Autowired
    private BookPriceService bookPriceService;

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

    @PostMapping("/book/price/createOrUpdate")
    public void createOrUpdateBookPrice(@RequestBody BookInfoPriceUploaderDao info) {
        bookPriceService.createOrUpdate(info.bookId, info.price, info.uploader);
    }

    @PostMapping("/book/prices")
    public List<BookInfoPriceDao> getPrices(@RequestBody List<String> bookIds) {
        return bookPriceService.getPrices(bookIds);
    }
}
