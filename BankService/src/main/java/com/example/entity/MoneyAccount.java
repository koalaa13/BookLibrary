package com.example.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = MoneyAccount.TABLE_NAME)
@Table(name = MoneyAccount.TABLE_NAME)
public class MoneyAccount {
    public static final String TABLE_NAME = "money_account";

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String userId;
    private BigDecimal balance;

    public MoneyAccount() {
    }

    public MoneyAccount(String userId, BigDecimal balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
