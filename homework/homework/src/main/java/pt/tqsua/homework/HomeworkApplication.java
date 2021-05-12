package pt.tqsua.homework;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HomeworkApplication {

	private static final Logger log = LoggerFactory.getLogger(HomeworkApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(HomeworkApplication.class, args);
	}

}
