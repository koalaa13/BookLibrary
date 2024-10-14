package com.example.dao;

import java.math.BigDecimal;

public class BookInfoForCreatorDao extends BookInfoDao {
    public BigDecimal price;
    public boolean inModeration;
    public boolean moderationSuccessful;
    public boolean published;

    public BookInfoForCreatorDao(
            BigDecimal price,
            boolean inModeration,
            boolean moderationSuccessful,
            String shortDescription,
            String author,
            String title,
            boolean published
    ) {
        this.title = title;
        this.author = author;
        this.shortDescription = shortDescription;
        this.price = price;
        this.inModeration = inModeration;
        this.moderationSuccessful = moderationSuccessful;
        this.published = published;
    }
}
