# Sonar Qube



## Inicialização

### Executar com Docker

> [Tutorial](https://docs.sonarqube.org/latest/setup/get-started-2-minutes/)

Para executar o Sonar Qube com docker basta executar o seguinte comando.

```bash
$ docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:latest
```

A sua interface passa a estar disponível em [localhost:9000](http://localhost:9000/).



### Configurar ambiente Maven

Correr o comando abaixo para saber qual a pasta do Maven.

```bash
$ mvn -X clean
# Line 2 has "Maven home: XXX"
```

Ir até à pasta `$MavenHome/conf` e editar o ficheiro `settings.xml` com as configurações disponíveis [aqui](https://docs.sonarqube.org/latest/analysis/scan/sonarscanner-for-maven/).

> Mudar `<sonar.host.url>` para localhost!



## Analisar projeto

#### Como um serviço (CI)

Para analisar um projeto basta criar um token ([tutorial](https://docs.sonarqube.org/latest/user-guide/user-token/)) em <u>User > My Account > Security</u>.

Depois, na raiz do projeto, correr o comando abaixo.

```bash
$ mvn clean verify sonar:sonar -Dsonar.login=<Generated Token>
```

Uma vez executado, os resultados da análise podem ser consultados na interface do Sonar Qube.



#### No IDE

É possível realizar análises ao código de forma integrada nos IDE.

O IntelliJ fornece uma ferramenta de inspeção de código por defeito, acessível através do <u>Menu > Analyse > Inspect Code...</u>.

É ainda possível integrar a análise do Sonar Qube. Para tal basta instalar o seguinte plugin: https://plugins.jetbrains.com/plugin/7973-sonarlint. A análise é feita depois através do <u>Menu > Analyse > SonarLint</u>.