package com.example.dao;

public class BookInfoDao {
    public String shortDescription;
    public String author;
    public String title;

    public BookInfoDao() {
    }

    public BookInfoDao(String shortDescription, String author, String title) {
        this.shortDescription = shortDescription;
        this.author = author;
        this.title = title;
    }
}
