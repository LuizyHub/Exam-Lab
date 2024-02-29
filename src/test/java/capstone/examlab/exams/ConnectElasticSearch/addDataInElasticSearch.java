package capstone.examlab.exams.ConnectElasticSearch;

import capstone.examlab.exams.entity.QuestionEntity;
import capstone.examlab.exams.repository.DriverQuizzesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class addDataInElasticSearch {
    @Configuration
    public static class TestConfig {

        public ElasticSearchClientConfig elasticSearchClientConfig() {
            return new ElasticSearchClientConfig();
        }
    }

    @Autowired

    private DriverQuizzesRepository driverQuizzesRepository;

    @Test
    public void loadQuizData() throws IOException {
        String jsonData = loadJsonDataFromFile("src/test/resources/refine_driver_test.json");
        List<QuestionEntity> questionEntities = Arrays.asList(/* Parse JSON data to List<Quiz> */);
        driverQuizzesRepository.saveAll(questionEntities);
    }

    private String loadJsonDataFromFile(String fileName) throws IOException {
        ClassPathResource resource = new ClassPathResource(fileName);
        byte[] jsonDataBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
        return new String(jsonDataBytes, StandardCharsets.UTF_8);
    }
}