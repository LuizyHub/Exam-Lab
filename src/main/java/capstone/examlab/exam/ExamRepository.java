package capstone.examlab.exam;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ExamRepository extends ElasticsearchRepository<Quiz, Long> {
    //쿼리 추가 작성해야함
}
