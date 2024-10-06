package com.example.repository;

import com.example.entity.ModerationRequest;
import org.springframework.data.repository.CrudRepository;

public interface ModerationRequestRepository extends CrudRepository<ModerationRequest, String> {
}
