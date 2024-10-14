package com.example.service.impl;

import java.time.Instant;

import com.example.entity.SubscriptionTransaction;
import com.example.repository.SubscriptionTransactionRepository;
import com.example.service.SubscriptionTransactionService;
import exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubscriptionTransactionServiceImpl implements SubscriptionTransactionService {
    @Autowired
    private SubscriptionTransactionRepository transactionRepository;

    @Override
    public void createPendingTransaction(String subscriptionId) {
        SubscriptionTransaction transaction = new SubscriptionTransaction();
        transaction.setCreatedAt(Instant.now());
        transaction.setSubscriptionId(subscriptionId);
        transaction.setStatus(SubscriptionTransaction.Status.PENDING);

        transactionRepository.save(transaction);
    }

    @Transactional
    @Override
    public void finishTransaction(String subscriptionId) {
        SubscriptionTransaction transaction = transactionRepository.findBySubscriptionId(subscriptionId)
                .orElseThrow(() -> new NoSuchEntityException("No such transaction for subscription with id " +
                        subscriptionId));
        transactionRepository.finishTransaction(transaction.getId());
    }
}
