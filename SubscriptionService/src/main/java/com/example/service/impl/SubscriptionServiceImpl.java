package com.example.service.impl;

import java.math.BigInteger;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import com.example.component.PriceCalculator;
import com.example.entity.UserSubscription;
import com.example.feign.BankServiceClient;
import com.example.feign.BookInfoServiceClient;
import com.example.repository.UserSubscriptionRepository;
import com.example.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Autowired
    private PriceCalculator priceCalculator;
    @Autowired
    private BookInfoServiceClient bookInfoServiceClient;
    @Autowired
    private BankServiceClient bankServiceClient;
    @Autowired
    private UserSubscriptionRepository userSubscriptionRepository;

    @Override
    public BigInteger getSubscriptionPriceByBooks(List<String> bookIds) {
        return priceCalculator.calculateSubscriptionPrice(bookInfoServiceClient.getPrices(bookIds));
    }

    @Transactional
    @Override
    public boolean createSubscription(String userId, List<String> bookIds, boolean infinite) {
        Instant now = Instant.now();
        Instant expireAt = now.plus(1, ChronoUnit.MONTHS);

        String id = UUID.randomUUID().toString();

        UserSubscription userSubscription = new UserSubscription();
        userSubscription.setUserId(userId);
        userSubscription.setCreatedAt(now);
        userSubscription.setExpireAt(expireAt);
        userSubscription.setBookIds(bookIds);
        userSubscription.setId(id);

        BigInteger price = getSubscriptionPriceByBooks(bookIds);
        userSubscription.setPrice(price);

        boolean transactionCreated = bankServiceClient.createTransaction(id);
        if (!transactionCreated) {
            return false;
        }

        userSubscriptionRepository.save(userSubscription);
        return true;
    }
}
