package com.example.service.impl;

import com.example.entity.BookInfo;
import com.example.exception.BadRequestSendToModerationException;
import com.example.repository.BookInfoRepository;
import com.example.service.BookModerationService;
import dao.BookInfoModerationDao;
import exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.HttpConstants;

@Service
public class BookModerationServiceImpl implements BookModerationService {
    @Autowired
    private BookInfoRepository bookInfoRepository;

    @Override
    public BookInfoModerationDao getBookInfoForModeration(String bookInfoId) {
        BookInfo bookInfo = bookInfoRepository.findById(bookInfoId)
                .orElseThrow(() -> new NoSuchEntityException(BookInfo.class, bookInfoId));
        if (bookInfo.isInModeration()) {
            throw new BadRequestSendToModerationException("Already in moderation");
        }
        if (bookInfo.isModerationSuccess()) {
            throw new BadRequestSendToModerationException("Moderation is already successful");
        }
        if (bookInfo.getPrice() == null) {
            throw new BadRequestSendToModerationException("Can't send to moderation because should specify a price");
        }

        BookInfoModerationDao dao = new BookInfoModerationDao();
        dao.author = bookInfo.getAuthor();
        dao.shortDescription = bookInfo.getShortDescription();
        dao.title = bookInfo.getTitle();
        dao.downloadUrl = buildDownloadLink(bookInfo.getFileUUID());
        dao.bookId = bookInfoId;

        checkAllFieldExistence(dao);

        return dao;
    }

    private void checkAllFieldExistence(BookInfoModerationDao dao) {
        if (dao.downloadUrl == null) {
            throw new BadRequestSendToModerationException("Can't send to moderation because there is no text file");
        }
        if (dao.author == null) {
            throw new BadRequestSendToModerationException("Can't send to moderation because there is no author");
        }
        if (dao.title == null) {
            throw new BadRequestSendToModerationException("Can't send to moderation because there is no title");
        }
        if (dao.shortDescription == null) {
            throw new BadRequestSendToModerationException("Can't send to moderation because there is no short description");
        }
    }

    private String buildDownloadLink(String fileUUID) {
        return fileUUID == null ?
                null :
                "http://" + HttpConstants.APP_DOMAIN + "/files/download/" + fileUUID;
    }
}
