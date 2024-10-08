package com.example.repository;

import com.example.entity.ModerationRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ModerationRequestRepository extends CrudRepository<ModerationRequest, String> {
    @Query(
            nativeQuery = true,
            value = "SELECT CASE WHEN (COUNT(*) > 0) THEN TRUE ELSE FALSE END" +
                    " FROM " + ModerationRequest.TABLE_NAME +
                    " WHERE book_info->>'bookId' = ? AND status = 'WAITING_MODERATOR'"
    )
    boolean existsUnmoderatedByBookId(String bookId);

    @Query(
            nativeQuery = true,
            value = "SELECT *" +
                    " FROM " + ModerationRequest.TABLE_NAME +
                    " WHERE status = 'WAITING_MODERATOR'" +
                    " ORDER BY created_at ASC" +
                    " LIMIT 1"
    )
    ModerationRequest getOldestNotProcessedModerationRequest();

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(
            value = "UPDATE " + ModerationRequest.TABLE_NAME +
                    " SET status = 'TAKEN_TO_WORK', moderatorId = :moderatorId" +
                    " WHERE id = :id AND status = 'WAITING_MODERATOR'"
    )
    int takeInWork(
            @Param("id") String moderationRequestId,
            @Param("moderatorId") String moderatorId
    );
}
