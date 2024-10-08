package com.example.controller;

import java.util.List;

import dao.ModerationResultItem;
import com.example.entity.ModerationResult;
import com.example.repository.ModerationResultRepository;
import exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ModerationResultPrivateController {
    @Autowired
    private ModerationResultRepository moderationResultRepository;

    @GetMapping("/moderation/{moderationResultId}/items")
    public List<ModerationResultItem> getModerationResultItems(@PathVariable("moderationResultId") String id) {
        ModerationResult result = moderationResultRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(ModerationResult.class, id));
        return result.getModerationResultItems();
    }
}
