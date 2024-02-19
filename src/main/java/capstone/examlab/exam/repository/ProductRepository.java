package capstone.examlab.exam.repository;

import capstone.examlab.exam.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

//Product에서 지정한 @Document(indexName = "products")에서만 작동 가능, 다른 index는 다른 repo필요
public interface ProductRepository extends ElasticsearchRepository<Product,Integer> {
}