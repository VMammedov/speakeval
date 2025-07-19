package org.project.speakeval.repository;

import org.project.speakeval.domain.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ExamRepository extends JpaRepository<Exam, String> {

    @Query(value = "SELECT e FROM Exam e JOIN FETCH e.questions ORDER BY FUNCTION('RANDOM') LIMIT 1")
    Optional<Exam> findRandomExamWithQuestions();

    Optional<Exam> findByTitle(String title);

    boolean existsByTitle(String title);

    boolean existsByTitleAndIdNot(String title, String id);

    @Query("SELECT e FROM Exam e WHERE e.title LIKE %:title%")
    Page<Exam> findByTitleContaining(@Param("title") String title, Pageable pageable);

    @Query("SELECT e FROM Exam e WHERE " +
            "(:title IS NULL OR e.title LIKE %:title%) AND " +
            "(:minScore IS NULL OR e.passScore >= :minScore) AND " +
            "(:minQuestions IS NULL OR SIZE(e.questions) >= :minQuestions)")
    Page<Exam> findByDynamicFilters(@Param("title") String title,
                                    @Param("minScore") Integer minScore,
                                    @Param("minQuestions") Integer minQuestions,
                                    Pageable pageable);

    @Modifying
    @Query("UPDATE Exam e SET e.passScore = :newScore WHERE e.id = :examId")
    int updatePassScore(@Param("examId") String examId, @Param("newScore") Integer newScore);
}
