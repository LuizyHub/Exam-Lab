package capstone.examlab;

import capstone.examlab.config.ElasticSearchDBConfig;
import capstone.examlab.questions.entity.QuestionEntity;
import capstone.examlab.questions.repository.DriverLicenseQuestionsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class ExamLabApplicationTests {
	@Autowired
	private DriverLicenseQuestionsRepository driverLicenseQuestionsRepository;

	@Test
	public void loadQuizData() throws IOException {
		String jsonData = loadJsonDataFromFile("driver_test_json.json");
		List<QuestionEntity> questionEntities = Arrays.asList(/* Parse JSON data to List<Quiz> */);
		driverLicenseQuestionsRepository.saveAll(questionEntities);
	}

	private String loadJsonDataFromFile(String fileName) throws IOException {
		ClassPathResource resource = new ClassPathResource(fileName);
		byte[] jsonDataBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
		return new String(jsonDataBytes, StandardCharsets.UTF_8);
	}
	@Configuration
	public static class TestConfig {
		@Bean
		public ElasticSearchDBConfig elasticSearchClientConfig() {
			return new ElasticSearchDBConfig();
		}
	}
}
