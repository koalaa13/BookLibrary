package com.example.dao;

import java.math.BigDecimal;

public class BookInfoDao {
    public String shortDescription;
    public String author;
    public String title;
    public boolean inModeration;
    public BigDecimal price;

    public BookInfoDao() {
    }

    public BookInfoDao(String shortDescription, String author, String title, boolean inModeration, BigDecimal price) {
        this.shortDescription = shortDescription;
        this.author = author;
        this.title = title;
        this.inModeration = inModeration;
        this.price = price;
    }
}
