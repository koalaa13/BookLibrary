package com.example.service;

import java.math.BigDecimal;
import java.util.List;

import dao.BookInfoPriceDao;

public interface BookPriceService {
    void createOrUpdate(String bookId, BigDecimal price, String uploader);
    void create(String bookId, String uploader);
    void update(String bookId, BigDecimal price);
    List<BookInfoPriceDao> getPrices(List<String> bookIds);
}
