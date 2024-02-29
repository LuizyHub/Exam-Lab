package capstone.examlab.exams.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Repository;

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
