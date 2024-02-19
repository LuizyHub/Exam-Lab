package capstone.examlab.exam.repository;

import capstone.examlab.exam.entity.Quiz;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DriverQuizzesRepository extends ElasticsearchRepository<Quiz,Integer> {

}
