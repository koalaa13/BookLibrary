package com.example.controller;

import java.util.List;

import com.example.dao.BookInfoDao;
import exception.EntityAccessPermissionDeniedException;
import com.example.feign.FileServiceClient;
import com.example.service.BookService;
import com.example.service.SendModerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import util.ContextHelper;

@RestController
@RequestMapping("/api/v1")
public class BookInfoController {
    @Autowired
    private BookService bookService;
    @Autowired
    private SendModerationService sendModerationService;
    @Autowired
    private FileServiceClient fileServiceClient;

    // TODO check file extension mb
    @PostMapping("/add")
    public String addBook(@RequestBody BookInfoDao bookInfoDao) {
        String uploader = ContextHelper.getCurrentUser();
        return bookService.createBookInfo(bookInfoDao, uploader).getId();
    }

    @PatchMapping("/update/file/{id}")
    public void addBookFile(@PathVariable("id") String id, @RequestParam(name = "file") MultipartFile file) {
        if (!"ADMIN".equals(ContextHelper.getCurrentUserRole()) &&
                !bookService.getBookInfoUploader(id).equals(ContextHelper.getCurrentUser())) {
            throw new EntityAccessPermissionDeniedException("Have no permissions to change another user's book");
        }
        String fileUUID = fileServiceClient.uploadFile(file).getUUID();
        bookService.updateBookFile(id, fileUUID);
    }

    @PatchMapping("/update/info/{id}")
    public void updateBook(@PathVariable("id") String id, @RequestBody BookInfoDao bookInfoDao) {
        if (!"ADMIN".equals(ContextHelper.getCurrentUserRole()) &&
                !bookService.getBookInfoUploader(id).equals(ContextHelper.getCurrentUser())) {
            throw new EntityAccessPermissionDeniedException("Have no permissions to change another user's book");
        }
        bookService.updateBookInfo(id, bookInfoDao);
    }

    @PostMapping("/sendToModeration/{id}")
    public void sendToModeration(@PathVariable("id") String id) {
        sendModerationService.sendBookOnModeration(id);
    }

    @GetMapping("/uploadedBy/{userId}")
    public List<BookInfoDao> getAllBooksUploadedBy(@PathVariable("userId") String userId) {
        String currentUser = ContextHelper.getCurrentUser();
        if (!"ADMIN".equals(ContextHelper.getCurrentUserRole()) && !userId.equals(currentUser)) {
            throw new EntityAccessPermissionDeniedException("Have no permissions to get another user's books");
        }
        return bookService.getAllBooksInfoByUploader(userId)
                .stream()
                .map(bookInfo -> new BookInfoDao(
                                bookInfo.getShortDescription(),
                                bookInfo.getAuthor(),
                                bookInfo.getTitle(),
                                bookInfo.isInModeration()
                        )
                )
                .toList();
    }
}
