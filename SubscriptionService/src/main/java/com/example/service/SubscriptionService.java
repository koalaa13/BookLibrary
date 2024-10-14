package com.example.service;

import java.math.BigDecimal;
import java.util.List;

public interface SubscriptionService {
    BigDecimal getSubscriptionPriceByBooks(List<String> bookIds);
    boolean createSubscription(String userId, List<String> bookIds, boolean infinite);
}
