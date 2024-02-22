package capstone.examlab.exams.repository;

import capstone.examlab.exams.entity.Quiz;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface DriverQuizzesRepository extends ElasticsearchRepository<Quiz,Integer> {
   // @Query("{\"bool\":{\"must\":[{\"terms\":{\"tags\":?0}}],\"filter\":{\"terms\":{\"tags\":?0}},\"should\":[{\"match_phrase\":{\"question\":?1}},{\"match_phrase\":{\"options\":?1}}],\"minimum_should_match\":1}}")
   // List<Quiz> findFirstBy(List<String> tags, int count, List<String> includes);*/
   @Query("{\"bool\":{\"should\":[{\"match_phrase\":{\"question\":?0}},{\"terms\":{\"options\":?1}}]}}")
   List<Quiz> findFirstBy(String include);
}
