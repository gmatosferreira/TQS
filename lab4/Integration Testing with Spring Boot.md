# Integration Testing with Spring Boot

Por norma, uma aplicação Spring Boot divide-se em vários componentes:

- **Entidade**, que representa o domínio conceptual;
- **Repositório**, que define os métodos de acesso aos dados para uma dada entidade;
- **Serviço**, que define a interface e implementação de um serviço, encapsulando a lógica do negócio;
- **Controlador**, a fronteira do sistema, que fornece um endpoint REST com uma API que recebe pedidos HTTP.

Para testar uma aplicação com todos estes componentes é necessário implementar testes abrangentes que cubram desde o funcionamento unitário de cada um, à interligação entre eles, até ao sistema como um todo. É então fundamental implementar uma bateria de testes em vários locais estratégicos.



## Testes em Spring Boot

Para facilitar a testagem de uma aplicação Spring Boot pode recorrer-se ao starter `spring-boot-starter-test`, que para além de fornecer um conjunto de dependências úteis aos testes, permite a sua auto-configuração.



### AssertJ

> [Tutorial](https://www.baeldung.com/introduction-to-assertj) e [Documentação](https://javadoc.io/static/org.assertj/assertj-core/3.19.0/org/assertj/core/api/Assert.html)

Esta é uma das dependências fornecidas pelo starter do Spring Boot. Permite criar asserções fluentes com base na sintaxe de <u>encadeamento de métodos</u>.

```java
import static org.assertj.core.api.Assertions.*;

// Basic assertion
assertThat(fido).isEqualTo(fidosClone);

// Chaining sintax assertion
assertThat(list).isNotEmpty().contains("1").doesNotContainNull().containsSequence("2", "3");

// .as() is used to describe the test and will be shown before the error message
assertThat(person.getAge())
  .as("%s's age should be equal to 100", person.getName())
  .isEqualTo(100);
```



## Cenários de teste

>NOTA
>
>Para fazer mocking do repositório é necessário mimificar o seu retorno, que para além de listas pode ser do tipo `Optional`. Abaixo são dados alguns exemplos de como criar instâncias desta classe.
>
>```java
>Optional.empty(); // Empty Optional object
>Optional.of(c1); // Optional with object
>```



### Acesso aos dados

Para <u>validar os serviços de acesso aos dados</u> fornecidos pelo repositório, <u>limita-se o seu contexto à instrumentação dos dados</u> através da anotação `@DataJpaTest`, que cria uma base de dados de testes em memória e injeta-se (`@Autowired`) um `TestEntityManager`, que permite gerir a base de dados de testes diretamente, sem passar pelo repositório, de forma a validar os seus métodos.

> **Dependências**
>
> org.springframework.beans.factory.annotation.Autowired
>
> [org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/test/autoconfigure/orm/jpa/DataJpaTest.html)
>
> [org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/test/autoconfigure/orm/jpa/TestEntityManager.html#flush--)

Os métodos da `TestEntityManager` mais comuns são `persist(E entity)` e `flush()`, que permitem adicionar entidades ao gestor (persist) e sincronizar (flush) a persistência com a base de dados. O `persistAndFlush(E entity)` permite fazer os dois em simultâneo.

```java
@DataJpaTest
class ObjectRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ObjectRepository objectRepository;

    @Test
    public void whenFindAlexByName_thenReturnAlexObject() {
        // Create object and persist on the db
        Object alex = new Object("alex", "alex@deti.com");
        entityManager.persistAndFlush(alex); //ensure data is persisted at this point

        // Call repository
        Object found = employeeRepository.findByName(alex.getName());
        // Validate response
        assertThat( found ).isEqualTo(alex);
    }
}
```



### Lógica do negócio

Para <u>validar a lógica do negócio</u> fornecida pela implementação dos serviços, criam-se <u>testes unitários com mocking do repositório</u> (JUnit + Mockito). Não há envolvimento da base de dados.

> <u>Não é necessário fazer Mock do método de save() no repositório, apenas dos finds.</u>
>
> Para além dos valores retornados pelo serviço, é importante validar também o número de chamadas aos métodos do repositório!

```java
@ExtendWith(MockitoExtension.class)
public class ObjectService_UnitTest {

    // lenient is required because we load some expectations in the setup
    // that are not used in all the tests. As an alternative, the expectations
    // could move into each test method and be trimmed: no need for lenient
    @Mock( lenient = true)
    private ObjectRepository objectRepository;

    @InjectMocks
    private ObjectService objectService;

    @BeforeEach
    public void setUp() {
        // Create entities and define employeeRepository mocking
        when(objectRepository.findAll()).thenReturn(Arrays.asList(obj1, obj2));
        // Attention that gets must return Optionals!
        when(carRepository.findById(c1.getId())).thenReturn(Optional.of(c1));
    }
    
    @Test
    public void testGetAll() {
        // Call service
        List<Object> allobjs = objectService.getAllObjects();
        // Test response (with AssertJ)
        assertThat(allobjs)
            .hasSize(2)
            .extracting(Object::getName)
            .contains(obj1.getName(), obj2.getName());
        // Alternative (for objects)
        assertThat(op.isPresent(), is(true));
        
        // Test repository usage
        Mockito.verify(objectRepository, VerificationModeFactory.times(1)).findAll();
    }
    
    @Test
    public void whenValidName_thenObjectShouldBeFound() {
        
    }
}
```



### Elementos fronteira (controlador)

Numa primeira <u>avaliação dos  controladores</u> não é necessário testar a API HTTP, sendo apenas necessário fazer testes <u>ao comportamento do controlador</u> em si para validar se este utiliza corretamente o serviço. Recorre-se para este fim a...

- Anotação `@WebMvcTest` na classe para simular o comportamento de um servidor aplicacional;
- Atributo em referência ao contexto do servidor do tipo `MockMvc` (anotado com `@Autowired`), que vai permitir fazer chamadas ao servidor;
- Atributo para o serviço, mocked com recurso à anotação `@MockBean`. Não há interação com o repositório.

> **Dependências**
>
> org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
>
> org.springframework.beans.factory.annotation.Autowired
>
> org.springframework.test.web.servlet.MockMvc;
>
> org.springframework.boot.test.mock.mockito.MockBean;

```java
@WebMvcTest(ObjectRestController.class)
public class ObjectController_WithMockService {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ObjectService service;

    @Test
    public void MyTest() {
        // Create object instances
        // Moch service
        given(service.getAllObjects()).thenReturn(Arrays.asList(obj1, obj2));
        
        // Make request to API and validate response
        mvc.perform(get("/api/objects").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(...);
        
        // Alternative (for POST requests)          
        mvc.perform(post("/api/employees")
        	.contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJson(obj1)))
            .andExpect(status().isCreated())
            .andExpect(...);;
            
		// Validate service usage
        verify(service, VerificationModeFactory.times(1)).getAllObjects();
    }
}
```

A validação da resposta do controlador é feita com recurso às asserções `andExpect(ResultMatcher handler)`, sendo que há vários tipos de handlers que podem ser utilizados. Abaixo são descritos alguns exemplos.

| Tipo de validação             | Instrução                                                    |
| ----------------------------- | ------------------------------------------------------------ |
| Status HTTP da resposta       | status().isOk()<br />status().isNotFound()<br />status().isCreated() |
| Conteúdo da resposta          | content().contentType(MediaType.APPLICATION_JSON)            |
| Cabeçalhos da resposta        | header().string(HttpHeaders.LOCATION, "/rest/widgets")       |
| Listas no payload da resposta | jsonPath("$", hasSize(2))<br />jsonPath("$[0].id", is(1))<br />jsonPath("$[1].name", is("Widget 2 Name")) |
| Objeto no payload da resposta | jsonPath("$.id", is(1))                                      |

> Retirado do tutorial do [infoworld.com](https://www.infoworld.com/article/3543268/junit-5-tutorial-part-2-unit-testing-spring-mvc-with-junit-5.html?page=3)



### Elementos fronteira  (REST SS)

Uma abordagem <u>menos localizada</u> consiste no <u>teste do controlador no lado do servidor</u>, que é feito através de uma classe anotada com `@SpringBootTest` (com ambiente web ativado) que vai inicializar o contexto web completo.  O acesso à API é feito através de um `MockMVC`, que fornece um entrypoint de suporte a testes do lado do servidor.

É um <u>teste de integração</u> e inclui a API REST, o serviço, o repositório e a base de dados.

```java
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = EmployeeMngrApplication.class)
@AutoConfigureMockMvc

// switch AutoConfigureTestDatabase with TestPropertySource to use a real database
// @TestPropertySource(locations = "application-integrationtest.properties")
@AutoConfigureTestDatabase
public class EmployeeRestControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EmployeeRepository repository;

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }
}
```



### Elementos fronteira (REST CS)

Por fim pode ainda ser feito um <u>teste de integração</u> completo, com origem em pedidos HTTP do cliente e que envolve todos os componentes da aplicação até à base de dados.

A classe de testes deve ser novamente anotada com `@SpringBootTest` (com ambiente web ativado) para inicializar o contexto web completo. Para fazer os pedidos do cliente pode recorrer-se à classe `TestRestTemplate` da biblioteca de testes Spring.

```java
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

// switch AutoConfigureTestDatabase with TestPropertySource to use a real database
@AutoConfigureTestDatabase
public class EmployeeRestControllerTemplateIT {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectRepository repository;

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }

}
```

Some test examples.

```java
@Test
public void whenValidInput_thenCreateCar() {
    // Create entity through controller
    Car c1 = new Car(123L, "BMW", "i3");
    ResponseEntity<Car> response = restTemplate.postForEntity("/api/cars", c1, Car.class);
    // Validate response
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody().getMaker()).isEqualTo(c1.getMaker());

    // Call repository to check that it was saved on the db
    List<Car> found = carRepository.findAll();
    // Validate response
    assertThat(found).extracting(Car::getMaker).containsOnly("BMW");
}

@Test
public void givenCars_whenGetCars_thenStatus200()  {
    // Save cars to db through repository
    carRepository.saveAndFlush(new Car(123L, "BMW", "i3"));
    carRepository.saveAndFlush(new Car(124L, "Renault", "Zoe"));

    // Call controller to get cars
    ResponseEntity<List<Car>> response = restTemplate.exchange("/api/cars", HttpMethod.GET, null, new ParameterizedTypeReference<List<Car>>() {});
    // Validate response
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).extracting(Car::getMaker).containsExactly("BMW", "Renault");
}
```



#### Utilizar base de dados real

A anotação `@AutoConfigureTestDatabase` vai fazer com que seja utilizada uma base de dados em memória H2 para a realização dos testes. Para utilizar uma base de dados real utiliza-se a anotação `@TestPropertySource`, sendo dada a localização do ficheiro com as propriedades.

```java
@TestPropertySource( locations = "application-integrationtest.properties")
```

Este ficheiro, por sua vez, deve definir as variáveis necessárias à conexão à BD. Abaixo é dado um exemplo.

> Deve ser guardado na pasta `/test/resources/<package>/application-integrationtest.properties`.

```properties
## note changed port 3306 --> 33060
spring.datasource.url=jdbc:mysql://localhost:33060/tqsdemo
spring.jpa.hibernate.ddl-auto=create-drop
spring.datasource.username=demo
spring.datasource.password=demo
```

Para correr uma base de dados MySQL em Docker basta correr o comando abaixo.

```bash
$ docker run --name mysql5tqs -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=tqsdemo -e MYSQL_USER=demo -e MYSQL_PASSWORD=demo -p 33060:3306 -d mysql/mysql-server:5.7
```

