package com.example.component.impl;

import com.example.entity.ModerationRequest;
import com.example.repository.ModerationRequestRepository;
import com.example.component.ModerationRequestSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreationDateModerationRequestSelector implements ModerationRequestSelector {
    @Autowired
    private ModerationRequestRepository moderationRequestRepository;

    @Override
    public ModerationRequest getModerationRequestToDo() {
        return moderationRequestRepository.getOldestNotProcessedModerationRequest();
    }
}
