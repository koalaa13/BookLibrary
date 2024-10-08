package com.example.feign;

import java.util.List;

import dao.ModerationResultItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "ModerationService", url = "http://localhost:8092/")
public interface ModerationServiceClient {
    @RequestMapping(method = RequestMethod.GET, value = "/moderation/{id}/items")
    List<ModerationResultItem> getModerationResultItems(@PathVariable("id") String moderationResultId);
}
