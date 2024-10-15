package com.example.service.impl;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.component.PriceCalculator;
import com.example.entity.UserSubscription;
import com.example.feign.BankServiceClient;
import com.example.repository.UserSubscriptionRepository;
import com.example.service.SubscriptionService;
import dao.SubscriptionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Autowired
    private PriceCalculator priceCalculator;
    @Autowired
    private BankServiceClient bankServiceClient;
    @Autowired
    private UserSubscriptionRepository userSubscriptionRepository;

    @Override
    public BigDecimal getSubscriptionPriceByBooks(List<String> bookIds) {
        return priceCalculator.calculateSubscriptionPrice(bankServiceClient.getPrices(bookIds));
    }

    @Override
    public List<SubscriptionDao> getSubscriptions(List<String> ids) {
        List<SubscriptionDao> res = new ArrayList<>();
        userSubscriptionRepository.findAllById(ids).forEach(s -> {
            res.add(new SubscriptionDao(s.getId(), s.getBookIds(), s.getPrice(), s.getUserId()));
        });
        return res;
    }

    @Override
    public List<String> getBooksByUser(String userId) {
        return userSubscriptionRepository.getNonExpiredSubscriptionsByUser(userId)
                .stream()
                .flatMap(s -> s.getBookIds().stream())
                .distinct()
                .toList();
    }

    @Transactional
    @Override
    public boolean createSubscription(String userId, List<String> bookIds, boolean infinite) {
        Instant now = Instant.now();
        Instant expireAt = infinite ?
                Instant.ofEpochSecond(7282894156L) : // some date in far-far future
                now.plus(30, ChronoUnit.DAYS);

        String id = UUID.randomUUID().toString();

        UserSubscription userSubscription = new UserSubscription();
        userSubscription.setUserId(userId);
        userSubscription.setCreatedAt(now);
        userSubscription.setExpireAt(expireAt);
        userSubscription.setBookIds(bookIds);
        userSubscription.setId(id);

        BigDecimal price = getSubscriptionPriceByBooks(bookIds);
        userSubscription.setPrice(price);

        boolean transactionCreated = bankServiceClient.createTransaction(id);
        if (!transactionCreated) {
            return false;
        }

        userSubscriptionRepository.save(userSubscription);
        return true;
    }
}
