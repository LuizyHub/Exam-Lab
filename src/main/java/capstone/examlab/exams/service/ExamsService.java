package capstone.examlab.exams.service;

import capstone.examlab.exams.dto.ExamList;
import capstone.examlab.questions.entity.QuestionEntity;

import java.util.List;

public interface ExamsService {
    public ExamList getExamList();
}
