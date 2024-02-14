package capstone.examlab.exam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExamServiceImpl implements ExamService{
    //여기에 repository, controller와 autowired 시켜주고 ExamService 메서드 구현해줘야함
    private final ExamRepository examRepository;

    @Autowired
    public ExamServiceImpl(ExamRepository examRepository){this.examRepository = examRepository;}

}
