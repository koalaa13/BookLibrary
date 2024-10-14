package com.example.service;

import java.math.BigDecimal;
import java.util.List;

import dao.SubscriptionDao;

public interface SubscriptionService {
    BigDecimal getSubscriptionPriceByBooks(List<String> bookIds);
    boolean createSubscription(String userId, List<String> bookIds, boolean infinite);
    List<SubscriptionDao> getSubscriptions(List<String> ids);
}
