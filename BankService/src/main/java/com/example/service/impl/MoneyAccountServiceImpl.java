package com.example.service.impl;

import java.math.BigDecimal;

import com.example.entity.MoneyAccount;
import com.example.repository.MoneyAccountRepository;
import com.example.service.MoneyAccountService;
import exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MoneyAccountServiceImpl implements MoneyAccountService {
    @Autowired
    private MoneyAccountRepository moneyAccountRepository;

    @Transactional
    @Override
    public void createAccount(String userId) {
        if (moneyAccountRepository.existsByUserId(userId)) {
            return;
        }
        MoneyAccount moneyAccount = new MoneyAccount(userId, BigDecimal.ZERO);
        moneyAccountRepository.save(moneyAccount);
    }

    @Transactional
    @Override
    public void addMoney(String userId, BigDecimal amount) {
        MoneyAccount account = moneyAccountRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchEntityException("No account for user " + userId));
        account.setBalance(account.getBalance().add(amount));
        moneyAccountRepository.save(account);
    }
}
