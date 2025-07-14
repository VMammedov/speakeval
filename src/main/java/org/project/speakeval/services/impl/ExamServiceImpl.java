package org.project.speakeval.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.project.speakeval.domain.Exam;
import org.project.speakeval.repository.ExamRepository;
import org.project.speakeval.services.ExamService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;

    public Exam getRandomExam() {
        return examRepository.findRandomExamWithQuestions()
                .orElseThrow(() -> new EntityNotFoundException("There is no exam at this time"));
    }

}
