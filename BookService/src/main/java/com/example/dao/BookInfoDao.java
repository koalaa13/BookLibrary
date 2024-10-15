package com.example.dao;

public class BookInfoDao {
    public String shortDescription;
    public String author;
    public String title;

    public BookInfoDao() {
    }

    public BookInfoDao(String title, String author, String shortDescription) {
        this.title = title;
        this.author = author;
        this.shortDescription = shortDescription;
    }
}
