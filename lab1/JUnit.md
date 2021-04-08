# JUnit

O JUnit é uma framework de testes em unidade open-source para código Java.



## Configuração em Maven

>  Para criar um projeto Maven.
>
> ```bash
> $ mvn archetype:generate -DgroupId=edu.pt.ua.tqs.lab2.s92972 -DartifactId=stock -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false
> 
> # To test
> $ cd ./<projFolder>
> $ mvn package
> $ java -cp target/<fileName>.jar edu.pt.ua.tqs.lab2.s92972.App
> ```



Podem ser adicionadas as dependências manualmente ao `pom.xml`. Ver mais [aqui](https://junit.org/junit5/docs/current/user-guide/#running-tests-build-maven).

```xml
<properties>
    <junit-jupiter.version>5.7.1</junit-jupiter.version>
</properties>

<dependencies>
    <!-- junit 5 -->
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-engine</artifactId>
        <version>${junit-jupiter.version}</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-api</artifactId>
        <version>${junit-jupiter.version}</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.junit.vintage</groupId>
        <artifactId>junit-vintage-engine</artifactId>
        <version>${junit-jupiter.version}</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

Em alternativa, através do **IntelliJ**, carregar sobre a classe para a qual queremos criar os testes e manter o rato, até aparecer a lâmpada das sugestões. Nas sugestões carregar em <u>Create test</u> e selecionar.

**Testing library** JUnit5

**Generate** Selecionar `@Before` e `@After` se estes métodos forem necessários.

**Generate test methods for** Selecionar os métodos para os quais gerar testes.

> Vai aparecer um aviso de que as dependências não estão configuradas. Carregar no botão para resolver. O aviso não desaparece, mas as dependências são adicionadas.

> A classe sobre a qual vão ser criados os testes já deve ter o esqueleto dos métodos definido, de forma a ser feito um mapeamento automático na classe de testes. No entanto, este métodos não devem ter qualquer funcionalidade, apenas um retorno (se necessário) por defeito para que o código possa ser compilado sem erros, de forma a cumprir a boa prática de <u>implementar os testes antes das funções que os implementam</u>, de forma a desenvolver código mais eficiente.



### Execução dos testes

Para **executar** os testes no IntelliJ ir ao menu Maven (lateral direita) > Lifecycle > Tests.



## Testes

Os métodos de teste são anotados com `org.junit.jupiter.api.Test` e são executados de forma independente.

> Não deve ser feita qualquer assunção quanto à ordem em que são executados. Não são relacionados entre si!

Para suspender a execução de um teste basta anotá-lo com `org.junit.jupiter.api.Disabled`, podendo passar como argumento uma string com o motivo pelo qual está suspenso.

```java
@Disabled("Disabled until bug #99 has been fixed")
```

Em cada execução é criada uma nova instância da classe de testes antes de invocar cada método `@Test`, podendo esta ser dividida em três fases.

![](./imgs/junit.jpeg)



### Preparação e limpeza 

Podem ser definidos métodos anotados com `org.junit.jupiter.api.BeforeEach` e `org.junit.jupiter.api.AfterEach`, que vão, respetivamente, ser corridos antes e depois de cada método de teste definido.

> O `setUp()` serve geralmente para inicializar os objetos para os testes. 
>
> É boa prática o `tearDown()` colocar estes objetos a `null`, para limpar a memória.

```java
@org.junit.jupiter.api.BeforeEach
void setUp() {
}

@org.junit.jupiter.api.AfterEach
void tearDown() {
}
```



### Validações

Para além de poderem ter algum código de preparação aos testes, o *core* será a validação se a classe tem o comportamento esperado naquele contexto. Para tal, o **JUnit** dispõe de várias asserções.

https://junit.org/junit5/docs/5.0.1/api/org/junit/jupiter/api/Assertions.html

```java
assertTrue(boolean condition);
assertFalse(boolean condition);
assertEquals(Object expected, Object actual);
assertThrows(Class<T> expectedType, Executable executable);
/*
assertThrows(
	InvalidParameterException.class, 
	() -> new ThrowingExceptionClass().doSomethingDangerous
);
*/
assertAll(String heading, Executable... executables);
/*
assertAll(
	"personTests", 
	() -> assertEqual("Jane", person.fName()), 
	() -> assertEqual("Doe", person.lName())
);
*/
```



Para que no relatório dos testes seja mostrada informação adicionar, os métodos de asserção aceitam um parâmetro adicional `String message`, que é uma mensagem customizada para descrever o erro e <u>é apenas mostrada caso o teste falhe</u>.

> Como o objetivo será os testes passarem, serão poucos os que falham. Por isso, caso a mensagem de erro seja complexa, por exemplo, se resultar da concatenação de Strings, será vantajoso utilizar **funções lambda**, uma vez que estas só serão executadas caso seja necessário mostrar o erro, evitando-se assim a manipulação das Strings quando os testes passam.
>
> ```java
> asssertFalse(..., () => "Erro:" + " " + "Loren ipsum...");
> ```



### Parametrização

> [Documentação](https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests)

Para correr um teste várias vezes com diferentes argumentos basta anotá-lo com `org.junit.jupiter.params.ParameterizedTest` e definir pelo menos um dado de entrada com a anotação `org.junit.jupiter.params.provider.ValueSource`, que aceita como argumento listas de vários tipos de dados, como strings, ints, doubles, booleans, entre outros. ([Ver todos](https://junit.org/junit5/docs/current/api/org.junit.jupiter.params/org/junit/jupiter/params/provider/ValueSource.html#strings()))

```java
@ParameterizedTest
@ValueSource(strings = { "racecar", "radar", "able was I ere I saw elba" })
void palindromes(String candidate) {
    assertTrue(StringUtils.isPalindrome(candidate));
}
```



### Personalização

Para facilitar a legibilidade do relatório, pode ser dado um nome ao teste, em vez de ser assumido o nome da sua função e da sua classe, através da anotação `import org.junit.jupiter.api.DisplayName`.

```java
@DisplayName("A special test case")
class DisplayNameDemo {

    @Test
    @DisplayName("Custom test name containing spaces")
    void testWithDisplayNameContainingSpaces() {
    }

}
```



## Cobertura

Para analisar a abrangência dos testes pode recorrer-se à ferramenta [JaCoCo](https://www.baeldung.com/jacoco).

> A versão do tutorial gera os relatórios de cobertura mas com erros (todos os elementos com cobertura 0/100, quando há testes definidos). Corrigi para a versão 0.8.5 e funcionou.





## Hamlet

> [Documentação](http://hamcrest.org/JavaHamcrest/tutorial) e [Guia Baeldung](https://www.baeldung.com/java-junit-hamcrest-guide)

O Hamlet é uma framework que permite a escrita de testes unitários em várias linguagens de programação, entre as quais Java. Permite a escrita de testes unitários e facilmente mais legíveis para o programador.

Para ser integrado num projeto Maven, basta adicionar as seguintes dependências.

```xml
<dependency>
    <groupId>org.hamcrest</groupId>
    <artifactId>hamcrest-all</artifactId>
    <version>1.3</version>
</dependency>
```

Depois pode ser utilizado em conjunto com o JUnit. Para os testes são providenciados vários métodos **matchers**.

> [Documentação](http://hamcrest.org/JavaHamcrest/javadoc/2.2/org/hamcrest/Matchers.html)

```java
@org.junit.jupiter.api.Test
public void myTest() {
	String a = "foo";
    String b = "FOO";
    assertThat(a, equalToIgnoringCase(b));    
}
```