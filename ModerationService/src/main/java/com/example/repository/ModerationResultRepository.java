package com.example.repository;

import com.example.entity.ModerationResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModerationResultRepository extends CrudRepository<ModerationResult, String> {
}
