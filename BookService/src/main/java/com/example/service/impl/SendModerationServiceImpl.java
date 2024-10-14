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

import static util.KafkaConstants.BOOK_SEND_MODERATION_TOPIC;

@Service
public class SendModerationServiceImpl implements SendModerationService {
    @Autowired
    private KafkaTemplate<String, BookInfoModerationDao> bookInfoModerationDaoKafkaTemplate;
    @Autowired
    private BookInfoRepository bookInfoRepository;
    @Autowired
    private BookModerationService bookModerationService;

    @Override
    @Transactional
    public void sendBookOnModeration(String bookId) {
        BookInfoModerationDao dao = bookModerationService.getBookInfoForModeration(bookId);

        bookInfoRepository.updateInModeration(bookId, true);

        try {
            bookInfoModerationDaoKafkaTemplate.send(BOOK_SEND_MODERATION_TOPIC, dao).get(2, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new InternalServerSendToModerationException("Error happened when sending book on moderation", e);
        }
    }
}
