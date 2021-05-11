# REST Assured

> [Site oficial](https://rest-assured.io/)

REST Assured é uma biblioteca Java que facilita o processo de validação de APIs REST, que geralmente retornam texto em formato JSON, através de uma sintaxe simples e intuitiva.

Essencialmente as suas instruções dividem-se em três momentos, o do **contexto**, dado pelo método `given()`, seguido da **ação**, dado pelo `when()` e por fim pela validação, com o método `then()`.



## Contexto

Permite definir configurações adicionais. 



## Ação

Geralmente consiste em fazer o pedido à API.

```java
.get("/api/objects");
```

 

## Validação

Há vários métodos que permitem validar o payload retornado.

```java
.body("attrName", is("3")); // For objects
.body("[3].attrName", is("3")); // For lists
```

Extrair atributos.

```java
.then().extract().path("id");
// Returns ArrayList<Integer> because attribute "id" is of type Integer
```

E outros que validam o tipo de resposta.

```java
.assertThat().statusCode(200);
```



> Podem ser consultados mais exemplos em https://github.com/rest-assured/rest-assured/wiki/Usage