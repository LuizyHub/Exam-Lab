package capstone.examlab.exams.service;

import capstone.examlab.exams.dto.ExamList;
import capstone.examlab.exams.entity.QuestionEntity;

import java.util.Collection;
import java.util.List;

public interface ExamsService {

    List<QuestionEntity> findByUserSearch(List<String> tags, int count, String includes);

    public ExamList getExamList();
}
