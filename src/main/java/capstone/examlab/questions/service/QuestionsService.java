package capstone.examlab.questions.service;

import capstone.examlab.questions.dto.ImageSaveDto;
import capstone.examlab.questions.dto.QuestionsList;
import capstone.examlab.questions.dto.QuestionsOption;
import capstone.examlab.questions.entity.QuestionEntity;

import java.util.List;

public interface QuestionsService {
    QuestionsList searchFromQuestions(Long examId, QuestionsOption questionsOption);

    boolean deleteQuestionsByExamId(Long examId);

    boolean deleteQuestionsByUuidList(List<String> uuidList);

    Long countAllQuestions();

    void saveQuestions(List<QuestionEntity> questionEntities);

    void deleteAllQuestions();

    List<String> saveImages(ImageSaveDto imageSaveDto);

    void deleteAllImages();
}
