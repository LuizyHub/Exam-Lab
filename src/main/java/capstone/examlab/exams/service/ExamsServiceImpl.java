package capstone.examlab.exams.service;

import capstone.examlab.exams.dto.ExamDetail;
import capstone.examlab.exams.dto.ExamList;
import capstone.examlab.exams.dto.SubExamDetail;
import capstone.examlab.exams.entity.ExamDetailEntity;
import capstone.examlab.exams.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import capstone.examlab.exams.entity.SubExamDetailEntity;
import capstone.examlab.exams.repository.DriverQuizzesRepository;
import capstone.examlab.exams.repository.ExamDetailRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamsServiceImpl implements ExamsService {
    private final DriverQuizzesRepository driverQuizzesRepository;
    private final ExamDetailRepository examDetailRepository;

    public ExamsServiceImpl(DriverQuizzesRepository driverQuizzesRepository, ExamDetailRepository examDetailRepository) {
        this.driverQuizzesRepository = driverQuizzesRepository;
        this.examDetailRepository = examDetailRepository;
    }

    @Override
    public List<Question> findByUserSearch(List<String> tags, int count, String includes) {
        Pageable pageable = PageRequest.of(0, count, Sort.by(Sort.Order.asc("id")));
        //Page<Quiz> quizPage = driverQuizzesRepository.findByTagsInAndQuestionContainingOrOptionsContaining(tags, includes, includes, pageable);
        Page<Question> quizPage = driverQuizzesRepository.findByQuestionContainingOrOptionsContaining(includes, includes, pageable);
        return quizPage.getContent();
    }

    @Override
    public ExamList getExamList() {
        List<ExamDetailEntity> examDetailEntities = examDetailRepository.findAll();
        List<ExamDetail> examDetails = examDetailEntities.stream()
                .map(this::convertToExamDetail)
                .collect(Collectors.toList());

        ExamList examList = new ExamList();
        examList.addAll(examDetails);
        return examList;
    }

    private ExamDetail convertToExamDetail(ExamDetailEntity examDetailEntity) {
        ExamDetail examDetail = new ExamDetail();
        examDetail.setTitle(examDetailEntity.getExamTitle());

        List<SubExamDetailEntity> subExamDetailEntities = examDetailEntity.getSubExams();
        List<SubExamDetail> subExamDetails = subExamDetailEntities.stream()
                .map(this::convertToSubExamDetail)
                .collect(Collectors.toList());

        examDetail.getSubExams().addAll(subExamDetails);
        return examDetail;
    }

    private SubExamDetail convertToSubExamDetail(SubExamDetailEntity subExamDetailEntity) {
        SubExamDetail subExamDetail = new SubExamDetail();
        subExamDetail.setExamId(subExamDetailEntity.getExamId());
        subExamDetail.setSubTitle(subExamDetailEntity.getSubExamTitle());
        return subExamDetail;
    }

}
