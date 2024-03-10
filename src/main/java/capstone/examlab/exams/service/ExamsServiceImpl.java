package capstone.examlab.exams.service;

import capstone.examlab.exams.dto.ExamDetail;
import capstone.examlab.exams.dto.ExamList;
import capstone.examlab.exams.entity.Exam;
import lombok.RequiredArgsConstructor;
import capstone.examlab.exams.entity.SubExamDetail;
import capstone.examlab.exams.repository.ExamDetailRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamsServiceImpl implements ExamsService {
    private final ExamDetailRepository examDetailRepository;


    @Override
    public ExamList getExamList() {
        List<Exam> examDetailEntities = examDetailRepository.findAll();
        List<ExamDetail> examDetails = examDetailEntities.stream()
                .map(this::convertToExamDetail)
                .collect(Collectors.toList());

        ExamList examList = new ExamList();
        examList.addAll(examDetails);
        return examList;
    }

    private ExamDetail convertToExamDetail(Exam exam) {
        ExamDetail examDetail = new ExamDetail();
        examDetail.setTitle(exam.getExamTitle());

        List<SubExamDetail> subExamDetailEntities = exam.getSubExams();
        List<capstone.examlab.exams.dto.SubExamDetail> subExamDetails = subExamDetailEntities.stream()
                .map(this::convertToSubExamDetail)
                .collect(Collectors.toList());

        examDetail.getSubExams().addAll(subExamDetails);
        return examDetail;
    }

    private capstone.examlab.exams.dto.SubExamDetail convertToSubExamDetail(SubExamDetail subExamDetailEntity) {
        capstone.examlab.exams.dto.SubExamDetail subExamDetail = new capstone.examlab.exams.dto.SubExamDetail();
        subExamDetail.setExamId(subExamDetailEntity.getExamId());
        subExamDetail.setSubTitle(subExamDetailEntity.getSubExamTitle());
        return subExamDetail;
    }

}
