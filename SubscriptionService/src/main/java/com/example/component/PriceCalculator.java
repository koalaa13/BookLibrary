package com.example.component;

import java.math.BigDecimal;
import java.util.List;

import dao.BookInfoPriceDao;

public interface PriceCalculator {
    BigDecimal calculateSubscriptionPrice(List<BookInfoPriceDao> prices);
}
