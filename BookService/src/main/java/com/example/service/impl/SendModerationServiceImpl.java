package com.example.service.impl;

import com.example.service.SendModerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static util.KafkaConstants.BOOK_TEXT_MODERATION_TOPIC;

@Service
public class SendModerationServiceImpl implements SendModerationService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplateString;

    @Override
    public void sendBookTextOnModeration(String textFileUUID) {
        kafkaTemplateString.send(BOOK_TEXT_MODERATION_TOPIC, textFileUUID);
    }
}
