package com.example.controller;

import java.util.List;

import com.example.dao.BookInfoDao;
import com.example.entity.BookInfo;
import com.example.service.BookService;
import com.example.service.SendModerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import util.ContextHelper;

@RestController
public class BookInfoController {
    @Autowired
    private BookService bookService;
    @Autowired
    private SendModerationService sendModerationService;

    // TODO check file extension mb
    @PostMapping("/add")
    public String addBook(@RequestParam(name = "file") MultipartFile file) {
        String uploader = ContextHelper.getCurrentUser();
        BookInfo bookInfo = bookService.createBookInfo(file, uploader);

        String fileUUID = bookInfo.getFileUUID();
        sendModerationService.sendBookTextOnModeration(fileUUID);

        return bookInfo.getId();
    }

    @PatchMapping("/update/{id}")
    public void updateBook(@PathVariable("id") String id, @RequestBody BookInfoDao bookInfoDao) {
        bookService.updateBookInfo(id, bookInfoDao);
    }

    @GetMapping("/uploaded")
    public List<BookInfoDao> getAllBooksOfCurrentUser() {
        return bookService.getAllBooksInfoByUploader(ContextHelper.getCurrentUser())
                .stream()
                .map(bookInfo -> new BookInfoDao(
                                bookInfo.getShortDescription(),
                                bookInfo.getAuthor(),
                                bookInfo.getTitle()
                        )
                )
                .toList();
    }
}
