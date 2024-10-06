package com.example.service.impl;

import com.example.service.SendModerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static util.KafkaConstants.BOOK_MODERATION_TOPIC;

@Service
public class SendModerationServiceImpl implements SendModerationService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplateString;

    @Override
    public void sendBookOnModeration(String bookInfoId) {
        kafkaTemplateString.send(BOOK_MODERATION_TOPIC, bookInfoId);
    }
}
