package capstone.examlab.exams.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import capstone.examlab.exams.entity.Quiz;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomDriverQuizzesRepositoryImpl implements CustomDriverQuizzesRepository {

        private final ElasticsearchOperations elasticsearchOperations;

        @Autowired
        public CustomDriverQuizzesRepositoryImpl(ElasticsearchOperations elasticsearchOperations) {
            this.elasticsearchOperations = elasticsearchOperations;
        }

     /*   @Override
        public List<Quiz> findByUserSearch(List<String> tags, int count, String includes) {
                Quiz quiz = elasticsearchOperations.queryBuilderWithGetQuery.getById(id.toString()), Person.class);
                return person;
        }*/
}
