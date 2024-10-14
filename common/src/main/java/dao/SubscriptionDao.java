package dao;

import java.math.BigDecimal;
import java.util.List;

public class SubscriptionDao {
    public String id;
    public List<String> booksIds;
    public BigDecimal price;

    public SubscriptionDao() {
    }

    public SubscriptionDao(String id, List<String> booksIds, BigDecimal price) {
        this.id = id;
        this.booksIds = booksIds;
        this.price = price;
    }
}
