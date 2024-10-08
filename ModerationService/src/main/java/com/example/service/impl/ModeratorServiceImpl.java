package com.example.service.impl;

import java.time.Instant;
import java.util.List;

import com.example.component.ModerationRequestSelector;
import com.example.dao.FailedModeratorAssignResponse;
import com.example.dao.ModerationResultItem;
import com.example.dao.ModeratorAssignResponse;
import com.example.dao.SuccessModeratorAssignResponse;
import com.example.entity.ModerationRequest;
import com.example.entity.ModerationResult;
import com.example.repository.ModerationRequestRepository;
import com.example.repository.ModerationResultRepository;
import com.example.service.ModeratorService;
import exception.EntityAccessPermissionDeniedException;
import exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.ContextHelper;

@Service
public class ModeratorServiceImpl implements ModeratorService {
    @Autowired
    private ModerationRequestSelector selector;
    @Autowired
    private ModerationRequestRepository moderationRequestRepository;
    @Autowired
    private ModerationResultRepository moderationResultRepository;

    private SuccessModeratorAssignResponse buildFromModerationRequest(ModerationRequest request) {
        SuccessModeratorAssignResponse response = new SuccessModeratorAssignResponse();
        response.shortDescription = request.getBookInfo().shortDescription;
        response.author = request.getBookInfo().author;
        response.downloadUrl = request.getBookInfo().downloadUrl;
        response.title = request.getBookInfo().title;
        response.requestId = request.getId();

        return response;
    }

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

        return buildFromModerationRequest(request);
    }

    @Override
    public List<SuccessModeratorAssignResponse> getAssignedTo(String moderatorId) {
        return moderationRequestRepository.findAllByModeratorId(moderatorId)
                .stream()
                .map(this::buildFromModerationRequest)
                .toList();
    }

    @Transactional
    @Override
    public String submitModerationResult(String requestId, List<ModerationResultItem> resultItems) {
        String currentUser = ContextHelper.getCurrentUser();
        ModerationRequest request = moderationRequestRepository.findById(requestId)
                .orElseThrow(() -> new NoSuchEntityException(ModerationRequest.class, requestId));

        if (!currentUser.equals(request.getModeratorId())) {
            throw new EntityAccessPermissionDeniedException(
                    "Can't submit moderation request because you are not assigned moderator"
            );
        }

        moderationRequestRepository.workDone(requestId);

        ModerationResult result = new ModerationResult();
        result.setModerationRequest(request);
        result.setModerationResultItems(resultItems);
        result.setCreatedAt(Instant.now());

        result = moderationResultRepository.save(result);

        // TODO send async via kafka here

        return result.getId();
    }
}
