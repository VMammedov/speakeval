package org.project.speakeval.repository;

import org.project.speakeval.domain.SessionQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionQuestionRepository extends JpaRepository<SessionQuestion, String> {
}
