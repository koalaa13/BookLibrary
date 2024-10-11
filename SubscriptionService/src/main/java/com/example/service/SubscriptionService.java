package com.example.service;

import java.math.BigInteger;
import java.util.List;

public interface SubscriptionService {
    BigInteger getSubscriptionPriceByBooks(List<String> bookIds);
    boolean createSubscription(String userId, List<String> bookIds, boolean infinite);
}
