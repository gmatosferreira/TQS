package edu.pt.ua.tqs.lab2.s92972.studentTestContainer;

import org.junit.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest
class StudentTestContainerApplicationTests {

	@Container
	public static PostgreSQLContainer container = new PostgreSQLContainer("postgres:latest")
			.withUsername("duke")
			.withPassword("password")
			.withDatabaseName("test");

	@Autowired
	private StudentRepository studentRepository;

	// requires Spring Boot >= 2.2.6
	@DynamicPropertySource
	static void properties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", container::getJdbcUrl);
		registry.add("spring.datasource.password", container::getPassword);
		registry.add("spring.datasource.username", container::getUsername);
	}

	@BeforeEach
	void contextLoads() {
		Student s = new Student();
		s.setName("Pessoa de Teste");
		s.setCourse("LEI");
		studentRepository.save(s);
	}

	@AfterEach
	void contextUnload() {
		// Assure that there are not students on the DB
		studentRepository.deleteAll();
	}

	@Test
	@Order(2)
	void checkInsertedExists() {
		List<Student> s = studentRepository.findByName("Pessoa de Teste");
		assertEquals(1, s.size(), "Student created does not exist!");
		assertEquals("LEI", s.get(0).getCourse(), "Student created does have expected course!");
	}

}
