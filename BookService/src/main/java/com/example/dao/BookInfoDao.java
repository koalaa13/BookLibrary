package com.example.dao;

public class BookInfoDao {
    public byte[] shortDescription;
    public String author;
    public String title;

    public BookInfoDao() {
    }

    public BookInfoDao(byte[] shortDescription, String author, String title) {
        this.shortDescription = shortDescription;
        this.author = author;
        this.title = title;
    }
}
