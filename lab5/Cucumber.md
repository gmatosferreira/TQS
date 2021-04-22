# Cucumber

> [Documentação](https://cucumber.io/docs), [Livro Oreilly](https://learning.oreilly.com/library/view/mastering-software-testing/9781787285736/6e1c1a32-79a7-492e-a452-894c9f646bfd.xhtml)

O Cucumber é uma ferramenta de testes open-source que permite a escrita de testes legíveis em linguagens naturais por qualquer pessoa através da linguagem [Gherkin](https://cucumber.io/docs/gherkin/reference/).



## Gherkin

Os testes em Gherkin, escritos em ficheiro com extensão `.feature`, podem ser escritos num dos vários idiomas suportados, devendo cada linha não branca iniciar-se com uma palavra-chave, seguida pelo texto na linguagem natural. 

Estes devem ser armazenados na pasta `/test/resources/<package>`.

> Quando uma linha não começa com uma palavra-chave é ignorada pelo Cucumber, sendo utilizada como descrição.

As principais palavras-chave são:

**Feature** Descrição de alto-nível do software a testar. Caso de uso.

**Scenario** Exemplo concreto que ilustra uma regra da lógica do negócio, sendo composto por 3 partes: contexto inicial (pré-condições), descrição do evento (ações do utilizador) e descrição do resultado  esperado (resultado da ação anterior). Estas partes são refletidas nas palavras-chave <u>Given, When e Then</u>.

**Background** Permite a declaração de passos que vão ser considerados em todos os cenários, para evitar a duplicação de regras.

> Semelhante ao `@BeforeAll` no JUnit.

**Scenario Outline** Cenários nos quais os passos são marcados com variáveis.

**Examples** Seguem sempre os anteriores e consistem numa tabela com valores para as variáveis declaradas.

```gherkin
Feature: Basic Arithmetic
  Background: A Calculator
    Given a calculator I just turned on
  Scenario: Addition
    When I add 4 and 5
    Then the result is 9
  Scenario Outline: Several additions
    When I add <a> and <b>
    Then the result is <c>
  Examples: Single digits
    | a | b | c  |
    | 1 | 2 | 3  |
    | 3 | 7 | 10 |
```



### Parâmetros

> [Documentação](https://cucumber.io/docs/cucumber/cucumber-expressions/)

Os valores são retirados das Strings em Gherkin através de expressões regulares ou de parâmetros, sendo os últimos mais fáceis e intuitivos de utilizar. Os parâmetros devem ser definidos entre chavetas e podem ser de vários tipos: 

`int` para inteiros;

`float` para números reais;

`word` para uma String delimitada por espaços;

`string` para qualquer conjunto de caracteres entre aspas.

```gherkin
I have {float} cucumbers in my belly
```

Cada parâmetro é depois mapeado para uma variável argumento da função de teste, como se pode ver no exemplo de código do próximo tópico.



#### Parâmetos personalizados

Para além dos parâmetros fornecidos pelo Gherkin, o programador pode definir os seus próprios parâmetros através da anotação `@ParameterType`.

```java
@ParameterType("red|blue|yellow")  // regexp
public Color color(String color){  // type, name (from method)
    return new Color(color);       // transformer function
}
```

```gherkin
I have a {color} ball
```



## Código dos testes

Uma vez definidas as funcionalidades a ser testadas em linguagem natural, é necessário definir os passos em código, que vão traduzir o texto na linguagem natural em ações.

Para tal, recorre-se a anotações como `@Given`, `@Then`, `@When`, `@And` e `@But`.

Para a criação do código dos testes, há dois métodos auxiliares:

- Através do **IntelliJ**, carregar sobre a regra para a qual queremos criar os testes e manter o rato, até aparecer a lâmpada das sugestões. Nas sugestões será dada uma opção para criar o código do teste;
- O Maven, ao corrermos os testes deteta os testes escritos que não têm correspondência em código e sugere uma implementação.

```java
import static org.junit.jupiter.api.Assertions.assertEquals;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CalculatorSteps {

    private Calculator calc;

    @Given("^a calculator I just turned on$")
    public void setup() {
        calc = new Calculator();
    }

    // Reading values with regular expression
    @When("^I add (\\d+) and (\\d+)$")
    public void add(int arg1, int arg2) {
        calc.push(arg1);
        calc.push(arg2);
        calc.push("+");
    }
    
    // Reading values with parameters (much easier!)
    @Then("the result is {int}")
    public void the_result_is(double expected) {
        assertEquals(expected, calc.value());
    }

}
```

Uma vez escritos, falta apenas implementá-los com o JUnit. Para tal, é necessário criar uma classe de testes anotada com `@Cucumber`, que vai injetar os testes do Cucumber.

> Esta classe serve apenas para indicar ao Cucumber a pasta onde os testes estão escritos.

```java
@Cucumber
public class CucumberTest {
}
```



### Limpeza

Por vezes é necessário fazer uma limpeza depois da execução de cada cenário. Para tal, utiliza-se a anotação `@After` do Cucumber.

```java
import io.cucumber.java.After;

@After
public void doSomethingAfter(Scenario scenario){
    // Your code here
}
```



## Execução

Para executar os testes pode recorrer-se ao Lifecycle>test.



## Idiomas

Os testes podem ser escritos em vários idiomas. Para tal, no ficheiro Gherkin basta escrever na primeira linha o seguinte comentário:

```gherkin
# language: pt
```

A partir daqui as palavras-chave podem ser utilizadas no idioma definido. As traduções podem ser consultadas [aqui](https://cucumber.io/docs/gherkin/languages/). Em português utiliza-se `Funcionalidade`, `Contexto`, `Cenário`, `Esquema do Cenário`, `Exemplos`, `Dado/a`, `Quando`, `Então`.

No código java, as anotações também estão disponíveis neste idioma. Por exemplo em português temos `@Dada`, `@Quando`, `@Entao`.