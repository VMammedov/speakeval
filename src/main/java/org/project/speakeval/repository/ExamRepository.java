package org.project.speakeval.repository;

import org.project.speakeval.domain.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ExamRepository extends JpaRepository<Exam, String> {

    @Query(value = "SELECT e FROM Exam e JOIN FETCH Question q ORDER BY FUNCTION('RANDOM') LIMIT 1")
    Optional<Exam> findRandomExamWithQuestions();
    
}
