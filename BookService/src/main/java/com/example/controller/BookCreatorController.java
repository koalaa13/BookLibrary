package com.example.controller;

import java.math.BigDecimal;
import java.util.List;

import com.example.dao.BookInfoDao;
import com.example.dao.BookInfoForCreatorDao;
import com.example.dao.ModerationResultResponse;
import com.example.feign.FileServiceClient;
import com.example.service.BookCreatorService;
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
public class BookCreatorController {
    @Autowired
    private BookCreatorService bookCreatorService;
    @Autowired
    private SendModerationService sendModerationService;
    @Autowired
    private FileServiceClient fileServiceClient;

    @PostMapping("/add")
    public String addBook(@RequestBody BookInfoDao bookInfoDao) {
        String uploader = ContextHelper.getCurrentUser();
        return bookCreatorService.createBookInfo(bookInfoDao, uploader).getId();
    }

    @PatchMapping("/update/file/{id}")
    public void addBookFile(@PathVariable("id") String id, @RequestParam(name = "file") MultipartFile file) {
        ContextHelper.checkEntityAccess(
                bookCreatorService.getBookInfoUploader(id),
                "Have no permissions to change another user's book"
        );
        String fileUUID = fileServiceClient.uploadFile(file).getUUID();
        bookCreatorService.updateBookFile(id, fileUUID);
    }

    @PatchMapping("/update/info/{id}")
    public void updateBook(@PathVariable("id") String id, @RequestBody BookInfoDao bookInfoDao) {
        ContextHelper.checkEntityAccess(
                bookCreatorService.getBookInfoUploader(id),
                "Have no permissions to change another user's book"
        );
        bookCreatorService.updateBookInfo(id, bookInfoDao);
    }

    @PatchMapping("/update/price/{id}")
    public void updateBookPrice(@PathVariable("id") String id, @RequestBody BigDecimal price) {
        ContextHelper.checkEntityAccess(
                bookCreatorService.getBookInfoUploader(id),
                "Have no permissions to change another user's book"
        );
        bookCreatorService.updateBookPrice(id, price);
    }

    @PatchMapping("/publish/{id}")
    public void publishBook(@PathVariable("id") String id) {
        ContextHelper.checkEntityAccess(
                bookCreatorService.getBookInfoUploader(id),
                "Have no permissions to publish another user's book"
        );
        bookCreatorService.publishBook(id);
    }

    @PostMapping("/sendToModeration/{id}")
    public void sendToModeration(@PathVariable("id") String id) {
        ContextHelper.checkEntityAccess(
                bookCreatorService.getBookInfoUploader(id),
                "Have no permissions to send to moderation another user's book"
        );
        sendModerationService.sendBookOnModeration(id);
    }

    @GetMapping("/uploadedBy/{userId}")
    public List<BookInfoForCreatorDao> getAllBooksUploadedBy(@PathVariable("userId") String userId) {
        ContextHelper.checkEntityAccess(
                userId,
                "Have no permissions to get another user's book"
        );
        return bookCreatorService.getAllBooksInfoByUploader(userId);
    }

    @GetMapping("/moderation/result/{id}")
    public ModerationResultResponse getModerationResult(@PathVariable("id") String id) {
        ContextHelper.checkEntityAccess(
                bookCreatorService.getBookInfoUploader(id),
                "Have no permissions to get moderation result of another user's book"
        );
        return bookCreatorService.buildModerationResultResponse(id);
    }
}
