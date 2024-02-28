package capstone.examlab.exams.repository;

import capstone.examlab.exams.entity.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface DriverQuizzesRepository extends ElasticsearchRepository<Quiz,Integer> {
    //Page<Quiz> findByTagsInAndQuestionContainingOrOptionsContaining(List<String> tags, String questionIncludes, String optionsIncludes, Pageable pageable);

    Page<Quiz> findByQuestionContainingOrOptionsContaining(String includes, String includes1, Pageable pageable);
}
