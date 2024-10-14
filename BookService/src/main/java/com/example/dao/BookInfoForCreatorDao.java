package com.example.dao;

import java.math.BigDecimal;

public class BookInfoForCreatorDao extends BookInfoDao {
    public BigDecimal price;
    public boolean inModeration;
    public boolean moderationSuccessful;

    public BookInfoForCreatorDao(
            BigDecimal price,
            boolean inModeration,
            boolean moderationSuccessful,
            String shortDescription,
            String author,
            String title
    ) {
        this.title = title;
        this.author = author;
        this.shortDescription = shortDescription;
        this.price = price;
        this.inModeration = inModeration;
        this.moderationSuccessful = moderationSuccessful;
    }
}
