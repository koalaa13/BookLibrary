package com.example.service;

import com.example.entity.ModerationRequest;
import com.example.repository.ModerationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static util.KafkaConstants.BOOK_MODERATION_TOPIC;

@Service
public class BookModerationConsumer {
    @Autowired
    private ModerationRequestRepository moderationRequestRepository;

    @KafkaListener(topics = BOOK_MODERATION_TOPIC, containerFactory = "kafkaListenerContainerFactoryString")
    public void acceptModerationRequest(String bookInfoId) {
        moderationRequestRepository.save(new ModerationRequest(bookInfoId));
    }
}
