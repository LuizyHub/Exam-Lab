package capstone.examlab.questions.repository;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BoolQueryBuilder {
    public Query buildBoolQuery() {
        // "must" 조건을 추가할 리스트 생성
        List<Query> mustQueries = new ArrayList<>();

        // "must" 조건에 해당하는 Term 쿼리들 추가
        mustQueries.add(new TermQuery.Builder().field("tags").value("category-상황").build()._toQuery());
        mustQueries.add(new TermQuery.Builder().field("tags").value("category-표지").build()._toQuery());

        // BoolQuery 빌더 생성
        BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();

        // "must" 조건 설정
        boolQueryBuilder.must(mustQueries);

        // BoolQuery 빌더로 BoolQuery 생성
        return boolQueryBuilder.build()._toQuery();
    }
}
