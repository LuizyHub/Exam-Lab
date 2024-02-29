package capstone.examlab.exams.service;

import capstone.examlab.exams.dto.ExamList;
import capstone.examlab.exams.entity.Question;

import java.util.List;

public interface ExamsService {

    List<Question> findByUserSearch(List<String> tags, int count, String includes);

    public ExamList getExamList();
}
