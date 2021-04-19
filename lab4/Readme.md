# Lab 4



1. a) Examples of AssertJ expressive methods chaining.

   ```java
   // EmployeeRepositoryTest, Line 65
   assertThat(allEmployees).hasSize(3).extracting(Employee::getName).containsOnly(alex.getName(), ron.getName(), bob.getName());
   
   // EmployeeService_UnitTest, Line 103
   assertThat(allEmployees).hasSize(3).extracting(Employee::getName).contains(alex.getName(), john.getName(), bob.getName());
   ```

   

1. b) An example in which the repository behavior is mocked and the database access is avoided is on business logic implementation test, made in class **EmployeeService_UnitTest**.

   ```java
   @Mock( lenient = true)
   private EmployeeRepository employeeRepository;
   ```

   ```java
   Mockito.when(employeeRepository.findByName(john.getName())).thenReturn(john);
   Mockito.when(employeeRepository.findByName(alex.getName())).thenReturn(alex);
   Mockito.when(employeeRepository.findByName("wrong_name")).thenReturn(null);
   Mockito.when(employeeRepository.findById(john.getId())).thenReturn(Optional.of(john));
   Mockito.when(employeeRepository.findAll()).thenReturn(allEmployees);
   Mockito.when(employeeRepository.findById(-99L)).thenReturn(Optional.empty());
   ```

   

1. c) Both `@Mock` and `@MockBean` are annotations to the Mockito library. However, while the first one is from the plain Mockito Library (org.Mockito.Mock), the second one is from the spring-boot-test library wrapping Mockito library (org.springframework.boot.test.mock.mockito.MockBean). 

   When we want to develop a unit test, by design is should be isolated from the other app components and so there is no need to start the whole context. That is why to test the service component we used `@Mock`.

   But some tests rely on the Spring Boot container and for those we should use `@MockBean` to mock one of the container beans.

   > Based on https://stackoverflow.com/questions/44200720/difference-between-mock-mockbean-and-mockito-mock



1. d) The file `application-integrationtest.properties` is where the properties for a real database access for testing purposes are described. 

   These tests are currently annotated with `@AutoConfigureTestDatabase`, which makes them use an in memory database. To use a real database it can be switched to `@TestPropertySource(locations = "application-integrationtest.properties")`.