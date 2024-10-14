package dao;

import java.math.BigDecimal;

public class BookInfoPriceDao {
    public String bookId;
    public BigDecimal price;

    public BookInfoPriceDao() {
    }

    public BookInfoPriceDao(String bookId, BigDecimal price) {
        this.bookId = bookId;
        this.price = price;
    }
}
