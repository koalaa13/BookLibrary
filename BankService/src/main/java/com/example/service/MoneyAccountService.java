package com.example.service;

import java.math.BigInteger;

public interface MoneyAccountService {
    void createAccount(String userId);
    void addMoney(String userId, BigInteger amount);
}
