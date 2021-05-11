# Jenkins



## Correr locamente

Descarregar ficheiro .war em https://www.jenkins.io/download/#downloading-jenkins e correr com o comando abaixo.

```bash
$ java -jar jenkins.war --httpPort=8080 [--enable-future-java] # For Java 13
```

O serviço deve ficar disponível em [localhost:8080](http://localhost:8080). É pedida uma password, que foi impressa no output do comando executado no terminal.

> Para não dar problemas o melhor é correr com o $JAVA_HOME para o JDK versão 11.