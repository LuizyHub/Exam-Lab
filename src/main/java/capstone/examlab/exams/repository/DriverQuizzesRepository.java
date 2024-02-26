package capstone.examlab.exams.repository;

import capstone.examlab.exams.entity.Quiz;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface DriverQuizzesRepository extends ElasticsearchRepository<Quiz,Integer>, CustomDriverQuizzesRepository {
}
