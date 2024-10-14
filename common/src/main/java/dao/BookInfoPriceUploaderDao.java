package dao;

import java.math.BigDecimal;

public class BookInfoPriceUploaderDao extends BookInfoPriceDao {
    public String uploader;

    public BookInfoPriceUploaderDao() {
    }

    public BookInfoPriceUploaderDao(String bookId, BigDecimal price, String uploader) {
        super(bookId, price);
        this.uploader = uploader;
    }
}
