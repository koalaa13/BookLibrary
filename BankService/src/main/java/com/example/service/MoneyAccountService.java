package com.example.service;

import java.math.BigInteger;

public interface MoneyAccountService {
    void addMoney(String userId, BigInteger amount);
}
