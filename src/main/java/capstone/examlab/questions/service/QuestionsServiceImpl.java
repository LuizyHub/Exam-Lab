package capstone.examlab.questions.service;


import capstone.examlab.questions.dto.Question;
import capstone.examlab.questions.dto.QuestionsList;
import capstone.examlab.questions.dto.QuestionsOption;
import capstone.examlab.questions.entity.QuestionEntity;
import capstone.examlab.questions.repository.DriverLicenseQuestionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionsServiceImpl implements QuestionsService {
    private final DriverLicenseQuestionsRepository driverLicenseQuestionsRepository;

    @Override
    public QuestionsList findByDriverLicenseQuestions(Long examId, QuestionsOption questionsOption) {
        Pageable pageable = PageRequest.of(0, questionsOption.getCount());
        Page<QuestionEntity> questionsPage;
        if(questionsOption.getTags()==null&questionsOption.getIncludes()==null){
            questionsPage = driverLicenseQuestionsRepository.findAll(pageable);
        }
        else if(questionsOption.getTags()==null){
            questionsPage = driverLicenseQuestionsRepository.findByQuestionContainingOrOptionsContaining(questionsOption.getIncludes(), questionsOption.getIncludes(), pageable);
        }
        else if(questionsOption.getIncludes()==null) {
            questionsPage = driverLicenseQuestionsRepository.findByTagsIn(questionsOption.getTags(), pageable);
        }
        else {
            questionsPage = driverLicenseQuestionsRepository.findByTagsInAndQuestionContainingOrOptionsContaining(questionsOption.getTags(), questionsOption.getIncludes(), questionsOption.getIncludes(), pageable);
        }

        QuestionsList questionsList = new QuestionsList();
        for (QuestionEntity entity : questionsPage) {
            Question question = Question.builder()
                    .id(entity.getId())
                    .type(entity.getType())
                    .question(entity.getQuestion())
                    .questionImagesIn(new ArrayList<>(entity.getQuestionImagesIn()))
                    .questionImagesOut(new ArrayList<>(entity.getQuestionImagesOut()))
                    .options(new ArrayList<>(entity.getOptions()))
                    .answers(new ArrayList<>(entity.getAnswers()))
                    .commentary(entity.getCommentary())
                    .commentaryImagesIn(new ArrayList<>(entity.getCommentaryImagesIn()))
                    .commentaryImagesOut(new ArrayList<>(entity.getCommentaryImagesOut()))
                    .tags(new ArrayList<>(entity.getTags()))
                    .build();
            questionsList.add(question);
        }
        return questionsList;
    }

    @Override
    public QuestionsList findAllByDriverLicneseQuestions() {
        Iterable<QuestionEntity> questionsPage;
        QuestionsList questionsList = new QuestionsList();
        questionsPage = driverLicenseQuestionsRepository.findAll();

        for (QuestionEntity entity : questionsPage) {
            Question question = Question.builder()
                    .id(entity.getId())
                    .type(entity.getType())
                    .question(entity.getQuestion())
                    .questionImagesIn(new ArrayList<>(entity.getQuestionImagesIn()))
                    .questionImagesOut(new ArrayList<>(entity.getQuestionImagesOut()))
                    .options(new ArrayList<>(entity.getOptions()))
                    .answers(new ArrayList<>(entity.getAnswers()))
                    .commentary(entity.getCommentary())
                    .commentaryImagesIn(new ArrayList<>(entity.getCommentaryImagesIn()))
                    .commentaryImagesOut(new ArrayList<>(entity.getCommentaryImagesOut()))
                    .tags(new ArrayList<>(entity.getTags()))
                    .build();
            questionsList.add(question);
        }
        return questionsList;
    }

    @Override
    public Long countAllByDriverLicenseQuestions() {
        return driverLicenseQuestionsRepository.count();
    }

    @Override
    public void saveDriverLicenseQuestions(List<QuestionEntity> questionEntities) {
        driverLicenseQuestionsRepository.saveAll(questionEntities);
    }

    @Override
    public void deleteDriverLicenseQuestions() {
        driverLicenseQuestionsRepository.deleteAll();
    }
}
