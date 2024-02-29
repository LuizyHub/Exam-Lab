package capstone.examlab.exams.repository;

import capstone.examlab.exams.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DriverQuizzesRepository extends ElasticsearchRepository<Question,Integer> {
    //Page<Quiz> findByTagsInAndQuestionContainingOrOptionsContaining(List<String> tags, String QuestionInclude, String OptionInclude, Pageable pageable);

    Page<Question> findByQuestionContainingOrOptionsContaining(String QuestionInclude, String OptionInclude, Pageable pageable);
}
