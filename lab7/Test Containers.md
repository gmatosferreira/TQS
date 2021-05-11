# Test Containers

Esta é uma livraria Java de suporte aos testes unitários JUnit que permite a sua execução em containers Docker. A sua utilização é bastante simples, resumindo-se aos seguintes passos:

1. Criação de ficheiro `test/resources/db/migration/V001__INIT.sql` com código SQL para criar a base de dados da entidade em teste;
2. Configurar a classe de teste (exemplo [aqui](https://github.com/rieckpil/blog-tutorials/blob/master/testcontainers-youtube-series/src/test/java/de/rieckpil/blog/ApplicationTests.java)).

> Pode ser útil definir uma ordem para a execução dos testes. Ver como [aqui](https://devqa.io/how-to-run-junit-5-tests-in-order/).