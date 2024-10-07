package com.example.service.impl;

import java.util.List;

import com.example.dao.BookInfoDao;
import com.example.entity.BookInfo;
import exception.NoSuchEntityException;
import com.example.feign.FileServiceClient;
import com.example.repository.BookInfoRepository;
import com.example.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookInfoRepository bookInfoRepository;
    @Autowired
    private FileServiceClient fileServiceClient;

    @Override
    public BookInfo createBookInfo(MultipartFile file, String uploader) {
        String UUID = fileServiceClient.uploadFile(file).getUUID();
        return bookInfoRepository.save(new BookInfo(UUID, uploader));
    }

    @Override
    public void updateBookInfo(String id, BookInfoDao bookInfoDao) {
        BookInfo bookInfo = bookInfoRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(BookInfo.class, id));

        bookInfo.setTitle(bookInfoDao.title);
        bookInfo.setAuthor(bookInfoDao.author);
        bookInfo.setShortDescription(bookInfoDao.shortDescription);
        bookInfoRepository.save(bookInfo);
    }

    @Override
    public List<BookInfo> getAllBooksInfoByUploader(String uploader) {
        return bookInfoRepository.findAllByUploader(uploader);
    }
}
