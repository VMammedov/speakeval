package org.project.speakeval.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.project.speakeval.constants.Constants;
import org.project.speakeval.domain.Exam;
import org.project.speakeval.domain.Question;
import org.project.speakeval.dto.client.CreateQuestionDto;
import org.project.speakeval.dto.client.request.exam.CreateExamRequest;
import org.project.speakeval.dto.client.request.exam.UpdateExamRequest;
import org.project.speakeval.dto.client.response.exam.ExamResponse;
import org.project.speakeval.dto.client.response.exam.ExamSummaryResponse;
import org.project.speakeval.enums.QuestionType;
import org.project.speakeval.exception.MissingFileException;
import org.project.speakeval.mapper.ExamMapper;
import org.project.speakeval.mapper.QuestionMapper;
import org.project.speakeval.repository.ExamRepository;
import org.project.speakeval.repository.QuestionRepository;
import org.project.speakeval.services.BlobStorageService;
import org.project.speakeval.services.ExamService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final ExamMapper examMapper;
    private final QuestionMapper questionMapper;
    private final BlobStorageService blobStorageService;

    @Override
    @Transactional(readOnly = true)
    public Page<ExamSummaryResponse> getAllExams(Pageable pageable) {
        return examRepository.findAll(pageable)
                .map(examMapper::toSummaryResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExamSummaryResponse> searchExams(String title, Integer minScore, Integer minQuestions, Pageable pageable) {
        return examRepository.findByDynamicFilters(title, minScore, minQuestions, pageable)
                .map(examMapper::toSummaryResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ExamResponse getExamById(String id) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Exam not found with id: " + id));
        return examMapper.toResponse(exam);
    }

    @Override
    @Transactional(readOnly = true)
    public ExamResponse getExamByTitle(String title) {
        Exam exam = examRepository.findByTitle(title)
                .orElseThrow(() -> new EntityNotFoundException("Exam not found with title: " + title));
        return examMapper.toResponse(exam);
    }

    @Override
    @Transactional
    public ExamResponse createExam(CreateExamRequest request) {
        validateTitleUniqueness(request.getTitle());

        Exam savedExam = examRepository.save(examMapper.toExam(request));

        List<Question> questions = request.getQuestions().stream()
                .map(questionDto -> createQuestionWithFile(questionDto, savedExam))
                .collect(Collectors.toCollection(ArrayList::new));

        savedExam.setQuestions(questions);
        examRepository.save(savedExam);

        return examMapper.toResponse(savedExam);
    }

    @Override
    public ExamResponse updateExam(String id, UpdateExamRequest request) {
        Exam existingExam = examRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Exam not found with id: " + id));

        if (request.getTitle() != null && !request.getTitle().equals(existingExam.getTitle())) {
            validateTitleUniquenessForUpdate(request.getTitle(), id);
        }

        examMapper.updateEntityFromRequest(request, existingExam);
        Exam updatedExam = examRepository.save(existingExam);
        return examMapper.toResponse(updatedExam);
    }

    @Override
    public void deleteExam(String id) {
        if (!examRepository.existsById(id)) {
            throw new EntityNotFoundException("Exam not found with id: " + id);
        }
        examRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(String id) {
        return examRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByTitle(String title) {
        return examRepository.existsByTitle(title);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExamSummaryResponse> getExamsByTitleContaining(String title, Pageable pageable) {
        return examRepository.findByTitleContaining(title, pageable)
                .map(examMapper::toSummaryResponse);
    }

    @Override
    public int updatePassScore(String id, Integer newScore) {
        if (!examRepository.existsById(id)) {
            throw new EntityNotFoundException("Exam not found with id: " + id);
        }
        return examRepository.updatePassScore(id, newScore);
    }

    @Override
    public ExamResponse duplicateExam(String id, String newTitle) {
        Exam originalExam = examRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Exam not found with id: " + id));

        validateTitleUniqueness(newTitle);

        Exam duplicateExam = Exam.builder()
                .title(newTitle)
                .description(originalExam.getDescription())
                .passScore(originalExam.getPassScore())
                .build();

        Exam savedExam = examRepository.save(duplicateExam);
        return examMapper.toResponse(savedExam);
    }

    private Question createQuestionWithFile(CreateQuestionDto questionDto, Exam exam) {
        Question question = questionMapper.toQuestion(questionDto);
        question.setExam(exam);

        Question savedQuestion = questionRepository.save(question);

        if (questionDto.getType().equals(QuestionType.IMAGE)) {
            if (questionDto.getFile() == null) {
                throw new MissingFileException("Missing image file for question");
            }

            String blobName = String.format(Constants.BlobNamingFormat.EXAM_IMAGE_QUESTION_FORMAT,
                    exam.getId(),
                    question.getId(),
                    questionDto.getFile());

            String url = blobStorageService.upload(
                    questionDto.getFile(),
                    blobName,
                    Constants.RelatedEntityType.QUESTION,
                    question.getId());

            savedQuestion.setPromptResourceUrl(url);
            questionRepository.save(savedQuestion);
        }

        return savedQuestion;
    }

    private void validateTitleUniqueness(String title) {
        if (examRepository.existsByTitle(title)) {
            throw new RuntimeException("Exam with title '" + title + "' already exists");
        }
    }

    private void validateTitleUniquenessForUpdate(String title, String examId) {
        if (examRepository.existsByTitleAndIdNot(title, examId)) {
            throw new RuntimeException("Exam with title '" + title + "' already exists");
        }
    }
}
