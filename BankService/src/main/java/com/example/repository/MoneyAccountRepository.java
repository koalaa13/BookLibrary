package com.example.repository;

import java.util.Optional;

import com.example.entity.MoneyAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneyAccountRepository extends CrudRepository<MoneyAccount, String> {
    Optional<MoneyAccount> findByUserId(String userId);
}
