package com.example.component;

import com.example.repository.BookInfoRepository;
import dao.ModerationResultDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static util.KafkaConstants.BOOK_SEND_MODERATION_RESULT_TOPIC;

@Component
public class ModerationResultConsumer {
    @Autowired
    private BookInfoRepository bookInfoRepository;

    @Transactional
    @KafkaListener(topics = BOOK_SEND_MODERATION_RESULT_TOPIC, containerFactory =
            "kafkaListenerContainerFactoryMessage")
    public void receiveModerationResult(ModerationResultDao dao) {
        if (bookInfoRepository.isModerated(dao.bookId)) {
            return;
        }
        bookInfoRepository.updateModerationResult(dao.bookId, dao.moderationResultId, dao.moderationSuccess);
    }
}
