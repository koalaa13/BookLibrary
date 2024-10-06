package com.example.repository;

import java.util.List;

import com.example.entity.BookInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BookInfoRepository extends CrudRepository<BookInfo, String> {
    @Transactional
    List<BookInfo> findAllByUploader(String uploader);
}
