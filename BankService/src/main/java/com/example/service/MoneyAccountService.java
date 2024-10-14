package com.example.service;

import java.math.BigDecimal;

public interface MoneyAccountService {
    void createAccount(String userId);
    void addMoney(String userId, BigDecimal amount);
}
