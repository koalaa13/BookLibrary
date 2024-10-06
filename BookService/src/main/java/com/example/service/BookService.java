package com.example.service;

import java.util.List;

import com.example.dao.BookInfoDao;
import com.example.entity.BookInfo;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {
    BookInfo createBookInfo(MultipartFile file, String uploader);
    void updateBookInfo(String id, BookInfoDao bookInfoDao);
    List<BookInfo> getAllBooksInfoByUploader(String uploader);
}
