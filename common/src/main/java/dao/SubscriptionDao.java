package dao;

import java.math.BigDecimal;
import java.util.List;

public class SubscriptionDao {
    public String id;
    public List<String> booksIds;
    public BigDecimal price;
    public String userId;

    public SubscriptionDao() {
    }

    public SubscriptionDao(String id, List<String> booksIds, BigDecimal price, String userId) {
        this.id = id;
        this.booksIds = booksIds;
        this.price = price;
        this.userId = userId;
    }
}
