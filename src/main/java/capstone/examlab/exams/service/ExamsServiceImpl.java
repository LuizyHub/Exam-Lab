package capstone.examlab.exams.service;

import capstone.examlab.exams.dto.ExamDetail;
import capstone.examlab.exams.dto.ExamList;
import capstone.examlab.exams.dto.SubExamDetail;
import capstone.examlab.exams.entity.ExamDetailEntity;
import capstone.examlab.exams.entity.Quiz;
import capstone.examlab.exams.entity.SubExamDetailEntity;
import capstone.examlab.exams.repository.DriverQuizzesRepository;
import capstone.examlab.exams.repository.ExamDetailRepository;
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
    public Iterable<Quiz> findByUserSearch(
            List<String> tags,
            int count,
            String includes
    ) {
        if (tags == null && includes == null) {
            // tags와 includes가 모두 null이면 모든 문제를 가져옴
            return driverQuizzesRepository.findAll();
        } else {
            // tags와 includes가 주어진 경우에는 쿼리 조건을 적용하여 문제를 가져옴
            //return driverQuizzesRepository.findFirstBy(tags, count, includes);
            return driverQuizzesRepository.findFirstBy(includes);
        }
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
