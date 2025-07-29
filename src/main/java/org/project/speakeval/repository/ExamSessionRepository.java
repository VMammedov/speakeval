package org.project.speakeval.repository;

import org.project.speakeval.domain.ExamSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExamSessionRepository extends JpaRepository<ExamSession, String> {

    @EntityGraph(attributePaths = "exam")
    Optional<ExamSession> findByIdAndUserId(String id, String userId);

    @EntityGraph(attributePaths = "exam")
    Page<ExamSession> findByUserId(String userId, Pageable pageable);
}
