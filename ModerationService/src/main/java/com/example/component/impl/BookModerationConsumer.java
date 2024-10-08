package com.example.component.impl;

import java.time.Instant;
import java.time.LocalDateTime;

import com.example.entity.ModerationRequest;
import com.example.repository.ModerationRequestRepository;
import dao.BookInfoModerationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static util.KafkaConstants.BOOK_MODERATION_TOPIC;

@Component
public class BookModerationConsumer {
    @Autowired
    private ModerationRequestRepository moderationRequestRepository;

    @Transactional
    @KafkaListener(topics = BOOK_MODERATION_TOPIC, containerFactory = "kafkaListenerContainerFactoryMessage")
    public void acceptModerationRequest(BookInfoModerationDao dao) {
        if (moderationRequestRepository.existsUnmoderatedByBookId(dao.bookId)) {
            return;
        }
        moderationRequestRepository.save(new ModerationRequest(dao, Instant.now()));
    }
}
