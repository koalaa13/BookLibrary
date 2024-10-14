package com.example.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.example.dao.BookInfoDao;
import com.example.dao.BookInfoForCreatorDao;
import com.example.dao.ModerationResultResponse;
import com.example.entity.BookInfo;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {
    BookInfo createBookInfo(BookInfoDao bookInfoDao, String uploader);
    String getBookInfoUploader(String id);
    void updateBookInfo(String id, BookInfoDao bookInfoDao);
    void updateBookFile(String id, String fileUUID);
    List<BookInfoForCreatorDao> getAllBooksInfoByUploader(String uploader);
    ModerationResultResponse buildModerationResultResponse(String id);
    void updateBookPrice(String id, BigDecimal price);
}
