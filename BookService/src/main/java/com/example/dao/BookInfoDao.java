package com.example.dao;

import java.math.BigInteger;

public class BookInfoDao {
    public String shortDescription;
    public String author;
    public String title;
    public boolean inModeration;
    public BigInteger price;

    public BookInfoDao() {
    }

    public BookInfoDao(String shortDescription, String author, String title, boolean inModeration, BigInteger price) {
        this.shortDescription = shortDescription;
        this.author = author;
        this.title = title;
        this.inModeration = inModeration;
        this.price = price;
    }
}
