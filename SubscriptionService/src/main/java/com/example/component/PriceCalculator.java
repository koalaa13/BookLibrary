package com.example.component;

import java.math.BigInteger;
import java.util.List;

import dao.BookInfoPriceDao;

public interface PriceCalculator {
    BigInteger calculateSubscriptionPrice(List<BookInfoPriceDao> prices);
}
