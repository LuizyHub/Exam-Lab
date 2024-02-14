package capstone.examlab;

import capstone.examlab.exam.ExamRepositoryImpl;
import capstone.examlab.exam.ExamService;
import capstone.examlab.exam.ExamServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ExamService examService() {return new ExamServiceImpl(examRepository());}

    @Bean
    public static ExamRepositoryImpl examRepository(){return new ExamRepositoryImpl();}

}
