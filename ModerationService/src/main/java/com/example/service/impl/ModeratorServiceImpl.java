package com.example.service.impl;

import com.example.component.ModerationRequestSelector;
import com.example.dao.FailedModeratorAssignResponse;
import com.example.dao.ModeratorAssignResponse;
import com.example.dao.SuccessModeratorAssignResponse;
import com.example.entity.ModerationRequest;
import com.example.repository.ModerationRequestRepository;
import com.example.service.ModeratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ModeratorServiceImpl implements ModeratorService {
    @Autowired
    private ModerationRequestSelector selector;
    @Autowired
    private ModerationRequestRepository moderationRequestRepository;

    @Override
    public ModeratorAssignResponse assign(String moderatorId) {
        ModerationRequest request = selector.getModerationRequestToDo();
        if (request == null) {
            return new FailedModeratorAssignResponse("There is no requests for moderation now");
        }

        boolean updated = moderationRequestRepository.takeInWork(
                request.getId(),
                moderatorId
        ) == 1;

        if (!updated) {
            return new FailedModeratorAssignResponse("This request is already processed");
        }

        SuccessModeratorAssignResponse response = new SuccessModeratorAssignResponse();
        response.shortDescription = request.getBookInfo().shortDescription;
        response.author = request.getBookInfo().author;
        response.downloadUrl = request.getBookInfo().downloadUrl;
        response.title = request.getBookInfo().title;

        return response;
    }
}
