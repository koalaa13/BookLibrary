package com.example.repository;

import java.util.List;

import com.example.entity.BookInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookInfoRepository extends CrudRepository<BookInfo, String> {
    List<BookInfo> findAllByUploader(String uploader);

    @Modifying
    @Query(value = "UPDATE " + BookInfo.TABLE_NAME +
            " SET inModeration = :inModeration" +
            " WHERE id = :id")
    void updateInModeration(@Param("id") String bookId, @Param("inModeration") boolean newValue);
}
