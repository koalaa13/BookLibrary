package com.example.dao;

public class BookInfoDao {
    public String shortDescription;
    public String author;
    public String title;
    public boolean inModeration;

    public BookInfoDao() {
    }

    public BookInfoDao(String shortDescription, String author, String title, boolean inModeration) {
        this.shortDescription = shortDescription;
        this.author = author;
        this.title = title;
        this.inModeration = inModeration;
    }
}
