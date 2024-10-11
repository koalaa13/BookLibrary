package com.example.service.impl;

import java.math.BigInteger;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

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

        UserSubscription userSubscription = new UserSubscription();
        userSubscription.setUserId(userId);
        userSubscription.setCreatedAt(now);
        userSubscription.setExpireAt(expireAt);
        userSubscription.setBookIds(bookIds);

        BigInteger price = getSubscriptionPriceByBooks(bookIds);
        userSubscription.setPrice(price);

        boolean moneySpent = bankServiceClient.addMoney(userId, price.negate());
        if (!moneySpent) {
            return false;
        }
        // TODO if app was killed here, then money was taken from reader, but subscription was not created :(
        // TODO book creators should get paid here too

        userSubscriptionRepository.save(userSubscription);
        return true;
    }
}
