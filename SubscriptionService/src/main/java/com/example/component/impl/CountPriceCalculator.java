package com.example.component.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.example.component.PriceCalculator;
import dao.BookInfoPriceDao;
import org.springframework.stereotype.Component;

@Component
public class CountPriceCalculator implements PriceCalculator {
    private static final Map<Integer, Double> DISCOUNTS_THRESHOLD = Map.of(
            5, 5.0,
            10, 10.0,
            30, 20.0
    );
    private static final BigInteger HUNDRED = new BigInteger("100");

    @Override
    public BigInteger calculateSubscriptionPrice(List<BookInfoPriceDao> prices) {
        BigInteger sum = prices.stream().map(dao -> dao.price).reduce(BigInteger.ZERO, BigInteger::add);
        double actualPercent = 100.0 - DISCOUNTS_THRESHOLD.keySet().stream()
                .filter(cnt -> cnt <= prices.size())
                .max(Integer::compareTo)
                .map(DISCOUNTS_THRESHOLD::get)
                .orElse(0.0);
        BigInteger mul = new BigInteger(Double.toString(actualPercent)).divide(HUNDRED);
        return sum.multiply(mul);
    }
}
