package com.example.repository;

import java.util.List;

import com.example.entity.UserSubscription;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSubscriptionRepository extends CrudRepository<UserSubscription, String> {
    @Query(
            nativeQuery = true,
            value = "SELECT *" +
                    " FROM " + UserSubscription.TABLE_NAME +
                    " WHERE expire_at > now() AND user_id = ?"
    )
    List<UserSubscription> getNonExpiredSubscriptionsByUser(String userId);
}
