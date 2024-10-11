package com.example.service.impl;

import java.math.BigInteger;
import java.util.Optional;

import com.example.entity.MoneyAccount;
import com.example.repository.MoneyAccountRepository;
import com.example.service.MoneyAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MoneyAccountServiceImpl implements MoneyAccountService {
    @Autowired
    private MoneyAccountRepository moneyAccountRepository;

    @Transactional
    @Override
    public void addMoney(String userId, BigInteger amount) {
        Optional<MoneyAccount> accountOptional = moneyAccountRepository.findByUserId(userId);
        if (accountOptional.isPresent()) {
            MoneyAccount account = accountOptional.get();
            account.setBalance(account.getBalance().add(amount));
            moneyAccountRepository.save(account);
        } else {
            moneyAccountRepository.save(new MoneyAccount(userId, amount));
        }
    }
}
