package capstone.examlab.exams.service;

import capstone.examlab.exams.dto.ExamDetail;
import capstone.examlab.exams.dto.ExamList;
import capstone.examlab.exams.dto.SubExamDetail;
import capstone.examlab.exams.entity.ExamDetailEntity;
import capstone.examlab.exams.entity.QuestionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import capstone.examlab.exams.entity.SubExamDetailEntity;
import capstone.examlab.exams.repository.DriverQuizzesRepository;
import capstone.examlab.exams.repository.ExamDetailRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.Collection;
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
    public List<QuestionEntity> findByUserSearch(List<String> tags, int count, String includes) {
        //정렬 또는 랜덤 적용 필요시 PageRequest.of 수정
        Pageable pageable = PageRequest.of(0, count);
        Page<QuestionEntity> quizPage;
        if(tags.isEmpty()&includes.equals("")){
            quizPage = driverQuizzesRepository.findAll(pageable);
            System.out.println("1번 작동");
        }
        else if(tags.isEmpty()){
            quizPage = driverQuizzesRepository.findByQuestionContainingOrOptionsContaining(includes, includes, pageable);
            System.out.println("2번 작동");
        }
        else if(includes.equals("")) {
            quizPage = driverQuizzesRepository.findByTagsIn(tags, pageable);
            System.out.println("3번작동");
        }
        else {
            quizPage = driverQuizzesRepository.findByTagsInAndQuestionContainingOrOptionsContaining(tags, includes, includes, pageable);
            System.out.println("4번 작동");
        }

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
