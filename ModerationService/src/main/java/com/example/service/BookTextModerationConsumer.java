package com.example.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static util.KafkaConstants.BOOK_TEXT_MODERATION_TOPIC;

@Service
public class BookTextModerationConsumer {

    @KafkaListener(topics = BOOK_TEXT_MODERATION_TOPIC, containerFactory = "kafkaListenerContainerFactoryString")
    public void acceptModerationRequest(String fileUUID) {
        // TODO write some logic
        System.out.println("Request to moderate file " + fileUUID + " was accepted");
    }
}
