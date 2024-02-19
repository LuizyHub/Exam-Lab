package capstone.examlab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

@SpringBootApplication
public class ExamLabApplication {
	public static void main(String[] args) {
		SpringApplication.run(ExamLabApplication.class, args);
	}
}
