package com.example.service.impl;

import java.time.Instant;
import java.util.List;

import com.example.dao.BookInfoDao;
import com.example.dao.ModerationResultMessageResponse;
import com.example.dao.ModerationResultResponse;
import com.example.dao.ModerationResultWithErrorsResponse;
import com.example.entity.BookInfo;
import com.example.exception.ChangeInModerationStatusException;
import com.example.feign.ModerationServiceClient;
import com.example.repository.BookInfoRepository;
import com.example.service.BookService;
import exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.StringUtil;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookInfoRepository bookInfoRepository;
    @Autowired
    private ModerationServiceClient moderationServiceClient;

    @Override
    public BookInfo createBookInfo(BookInfoDao bookInfoDao, String uploader) {
        return bookInfoRepository.save(
                new BookInfo(
                        bookInfoDao.shortDescription,
                        bookInfoDao.author,
                        bookInfoDao.title,
                        uploader,
                        Instant.now(),
                        bookInfoDao.price
                )
        );
    }

    @Override
    public String getBookInfoUploader(String id) {
        return bookInfoRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(BookInfo.class, id))
                .getUploader();
    }

    @Override
    public void updateBookInfo(String id, BookInfoDao bookInfoDao) {
        BookInfo bookInfo = bookInfoRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(BookInfo.class, id));
        if (bookInfo.isInModeration()) {
            throw new ChangeInModerationStatusException("Can't update book info because it sent to moderation");
        }

        if (!StringUtil.isEmpty(bookInfoDao.title)) {
            bookInfo.setTitle(bookInfoDao.title);
        }
        if (!StringUtil.isEmpty(bookInfoDao.author)) {
            bookInfo.setAuthor(bookInfoDao.author);
        }
        if (!StringUtil.isEmpty(bookInfoDao.shortDescription)) {
            bookInfo.setShortDescription(bookInfoDao.shortDescription);
        }
        bookInfoRepository.save(bookInfo);
    }

    @Override
    public void updateBookFile(String id, String fileUUID) {
        BookInfo bookInfo = bookInfoRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(BookInfo.class, id));
        if (bookInfo.isInModeration()) {
            throw new ChangeInModerationStatusException("Can't update book info because it sent to moderation");
        }

        if (!StringUtil.isEmpty(fileUUID)) {
            bookInfo.setFileUUID(fileUUID);
        }
        bookInfoRepository.save(bookInfo);
    }

    @Override
    public List<BookInfo> getAllBooksInfoByUploader(String uploader) {
        return bookInfoRepository.findAllByUploader(uploader);
    }

    @Override
    public ModerationResultResponse buildModerationResultResponse(String id) {
        BookInfo bookInfo = bookInfoRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(BookInfo.class, id));
        String moderationResultId = bookInfo.getModerationResultId();
        if (moderationResultId == null) {
            return new ModerationResultMessageResponse(
                    bookInfo.isInModeration() ?
                            "Can't get moderation result because books moderation is not finished yet" :
                            "Book was not send to moderation"
            );
        } else {
            return bookInfo.isModerationSuccess() ?
                    new ModerationResultMessageResponse("Moderation is successful") :
                    new ModerationResultWithErrorsResponse(
                            "Moderators found some errors",
                            moderationServiceClient.getModerationResultItems(moderationResultId)
                    );
        }
    }
}
