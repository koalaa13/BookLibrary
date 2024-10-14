package com.example.repository;

import java.util.List;
import java.util.Optional;

import com.example.entity.SubscriptionTransaction;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SubscriptionTransactionRepository extends CrudRepository<SubscriptionTransaction, String> {
    Optional<SubscriptionTransaction> findBySubscriptionId(String subscriptionId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(
            value = "UPDATE " + SubscriptionTransaction.TABLE_NAME +
                    " SET status = 'PROCESSED'" +
                    " WHERE subscriptionId IN (:subscriptionIds) AND status = 'PENDING'"
    )
    void processTransactions(@Param("subscriptionIds") Iterable<String> subscriptionIds);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(
            value = "UPDATE " + SubscriptionTransaction.TABLE_NAME +
                    " SET status = 'CANCELED'" +
                    " WHERE subscriptionId IN (:subscriptionIds) AND status = 'PENDING'"
    )
    void cancelTransactions(@Param("subscriptionIds") Iterable<String> subscriptionIds);

    @Query(
            nativeQuery = true,
            value = "SELECT * " +
                    " FROM " + SubscriptionTransaction.TABLE_NAME +
                    " WHERE status = 'PENDING'" +
                    " LIMIT ?"
    )
    List<SubscriptionTransaction> findPending(int limit);
}
