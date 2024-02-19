package capstone.examlab.exam.ConnectElasticSearch;

import capstone.examlab.exam.service.ElasticsearchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ElasticsearchServiceTest {

    @Autowired
    private ElasticsearchService elasticsearchService;

    @Test
    public void testElasticsearchService() {
        assertNotNull(elasticsearchService); // 서비스가 올바르게 주입되었는지 확인합니다.
        // 여기에 서비스의 각 메서드를 호출하여 동작을 테스트하는 코드를 작성합니다.
    }
}