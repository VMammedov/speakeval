package org.project.speakeval.repository;

import org.project.speakeval.domain.ExamSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamSessionRepository extends JpaRepository<ExamSession, String> {

}
