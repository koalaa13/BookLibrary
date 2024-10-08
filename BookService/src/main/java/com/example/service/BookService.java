package com.example.service;

import java.util.List;

import com.example.dao.BookInfoDao;
import com.example.entity.BookInfo;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {
    BookInfo createBookInfo(BookInfoDao bookInfoDao, String uploader);
    String getBookInfoUploader(String id);
    void updateBookInfo(String id, BookInfoDao bookInfoDao);
    void updateBookFile(String id, String fileUUID);
    List<BookInfo> getAllBooksInfoByUploader(String uploader);
}
