package capstone.examlab.questions.service;

import capstone.examlab.questions.dto.*;
import capstone.examlab.questions.entity.QuestionEntity;

import java.util.List;

public interface QuestionsService {
    void addQuestionsByExamId(Long examId, QuestionsUploadList questionsUploadList);

    boolean addImageInQuestions(ImagesUploadInfo imagesUploadInfo);

    QuestionsList searchFromQuestions(Long examId, QuestionsSearchDto questionsSearchDto);

    boolean deleteQuestionsByExamId(Long examId);

    boolean deleteQuestionsByUuidList(List<String> uuidList);

    Long countAllQuestions();

    void saveQuestions(List<QuestionEntity> questionEntities);

    void deleteAllQuestions();

    List<String> saveImages(ImagesSaveDto imageSaveDto);

    void deleteAllImages();
}
