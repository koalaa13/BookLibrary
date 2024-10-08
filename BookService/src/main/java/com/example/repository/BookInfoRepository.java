package com.example.repository;

import java.util.List;

import com.example.entity.BookInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BookInfoRepository extends CrudRepository<BookInfo, String> {
    List<BookInfo> findAllByUploader(String uploader);

    @Modifying
    @Query(value = "UPDATE " + BookInfo.TABLE_NAME +
            " SET inModeration = :inModeration" +
            " WHERE id = :id")
    void updateInModeration(@Param("id") String bookId, @Param("inModeration") boolean newValue);

    @Query(
            value = "SELECT moderationResultId IS NOT NULL" +
                    " FROM " + BookInfo.TABLE_NAME +
                    " WHERE id = :id"
    )
    boolean isModerated(@Param("id") String bookId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(
            value = "UPDATE " + BookInfo.TABLE_NAME +
                    " SET moderationResultId = :moderationResultId" +
                    " WHERE id = :id"
    )
    void updateModerationResult(@Param("id") String bookId, @Param("moderationResultId") String moderationResultId);
}
