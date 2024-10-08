package com.example.service.impl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.example.exception.BadRequestSendToModerationException;
import dao.BookInfoModerationDao;
import com.example.exception.InternalServerSendToModerationException;
import com.example.repository.BookInfoRepository;
import com.example.service.BookModerationService;
import com.example.service.SendModerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static util.KafkaConstants.BOOK_MODERATION_TOPIC;

@Service
public class SendModerationServiceImpl implements SendModerationService {
    @Autowired
    private KafkaTemplate<String, BookInfoModerationDao> kafkaTemplateBooKInfoModerationDao;
    @Autowired
    private BookInfoRepository bookInfoRepository;
    @Autowired
    private BookModerationService bookModerationService;

    @Override
    @Transactional
    public void sendBookOnModeration(String bookId) {
        BookInfoModerationDao dao = bookModerationService.getBookInfoForModeration(bookId);
        checkAllFieldExistence(dao);

        bookInfoRepository.updateInModeration(bookId, true);

        try {
            kafkaTemplateBooKInfoModerationDao.send(BOOK_MODERATION_TOPIC, dao).get(2, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new InternalServerSendToModerationException("Error happened when sending book on moderation", e);
        }
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
}
