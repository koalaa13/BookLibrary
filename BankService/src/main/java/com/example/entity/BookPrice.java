package com.example.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = BookPrice.TABLE_NAME)
@Table(name = BookPrice.TABLE_NAME)
public class BookPrice {
    public static final String TABLE_NAME = "book_price";

    @Id
    private String bookId;
    private BigDecimal price;
    private String uploader;

    public BookPrice() {
    }

    public BookPrice(String bookId, BigDecimal price, String uploader) {
        this.bookId = bookId;
        this.price = price;
        this.uploader = uploader;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }
}
