package com.demo.backend.repository;

import com.demo.backend.Entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, UUID> {
    public List<Feedback> findAllByEventId(UUID eventId);
}
