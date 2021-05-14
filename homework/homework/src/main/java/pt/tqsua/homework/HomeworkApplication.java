package pt.tqsua.homework;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class HomeworkApplication {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	private static final Logger log = LoggerFactory.getLogger(HomeworkApplication.class);

	public static void main(String[] args) {
		log.debug("Starting app...");
		SpringApplication.run(HomeworkApplication.class, args);
	}

}
