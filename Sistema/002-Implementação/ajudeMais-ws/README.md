Ajude Mais - WS
===============

[![Build Status](https://travis-ci.org/AjudeMais/AjudeMais.svg?branch=master)](https://travis-ci.org/AjudeMais/AjudeMais)


API RESTful Ajude Mais.

 
### ESTRUTURA
Projeto subdividido em módulos usando o maven https://maven.apache.org/guides/mini/guide-multiple-modules.html 
```
├── ajudeMais-ws/
│   |
│   ├── RESTful-api
│   │   |
│   │   ├── src/main
│   │   │     ├── java
│   │   │          ├──br.edu.ifpb.ajudeMais.api
│   │   |── src/test
│   │   │     ├── java
│   │   │          ├──br.edu.ifpb.ajudeMais.api
|   |	├──── pom.xml
│   ├── data
│   │   |
│   │   ├── src/main
│   │   │     ├── java
│   │   │          ├──br.edu.ifpb.ajudeMais.data
│   │   |── src/test
│   │   │     ├── java
│   │   │          ├──br.edu.ifpb.ajudeMais.data
|   |	├──── pom.xml
│   ├── domain
│   │   |
│   │   ├── src/main
│   │   │     ├── java
│   │   │          ├──br.edu.ifpb.ajudeMais.domain
|   |	├──── pom.xml
│   ├── service
│   │   |
│   │   ├── src/main
│   │   │     ├── java
│   │   │          ├──br.edu.ifpb.ajudeMais.service
│   │   |── src/test
│   │   │     ├── java
│   │   │          ├──br.edu.ifpb.ajudeMais.service
|   |	├──── pom.xml
├── pom.xml
```

REQUISITOS
===============
* JDK 8
 
