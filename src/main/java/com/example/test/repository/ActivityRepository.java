package com.example.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.test.model.Activity;
import java.util.List;
import java.time.LocalDateTime;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    // Active Record-like methods
    List<Activity> findByPetId(Long petId);
    List<Activity> findByType(String type);
    List<Activity> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
    List<Activity> findByPetIdAndType(Long petId, String type);
} 