Projeto base - Spring e AngularJS
===============
Arquitetura base para aplicação com Spring boot e angularJS.

BACKEND
===============
* Java 1.8
* Spring Boot 1.4.1
  -Spring Web (Spring MVC)
  -Spring Data (JPA)
  -Spring Validation
  -Spring Security
* Spring tests
* Junit
* DBunit
 
### ESTRUTURA
Projeto subdividido em módulos usando o maven https://maven.apache.org/guides/mini/guide-multiple-modules.html 
```
├── sigap/
│   |
│   ├── api
│   │   |
│   │   ├── src/main
│   │   │     ├── java
│   │   │          ├──br.com.hyperactive.sigap.api
│   │   |── src/test
│   │   │     ├── java
│   │   │          ├──br.com.hyperactive.sigap.api
|   |	├──── pom.xml
│   ├── data
│   │   |
│   │   ├── src/main
│   │   │     ├── java
│   │   │          ├──br.com.hyperactive.sigap.data
│   │   |── src/test
│   │   │     ├── java
│   │   │          ├──br.com.hyperactive.sigap.data
|   |	├──── pom.xml
│   ├── domain
│   │   |
│   │   ├── src/main
│   │   │     ├── java
│   │   │          ├──br.com.hyperactive.sigap.domain
|   |	├──── pom.xml
│   ├── service
│   │   |
│   │   ├── src/main
│   │   │     ├── java
│   │   │          ├──br.com.hyperactive.sigap.service
│   │   |── src/test
│   │   │     ├── java
│   │   │          ├──br.com.hyperactive.sigap.service
|   |	├──── pom.xml
├── pom.xml
```


FRONTEND
===============
* AngularJS 1.X (https://angularjs.org/)
* Booststrap 3.X (getbootstrap.com/)
* Font Awesome 4.7 (http://fontawesome.io/icons/)

### ESTRUTURA
Estrutura baseada nos style guides do John Papa (evagelizador angular).
```
├── webapp/
│   |
│   ├── app
│   │   |
│   │   ├── blocks
│   │   │     ├── config			
│   │   │     |    ├── http.config.js 
│   │   │     ├── handler				
│   │   │     |    ├─ ...
│   │   │     ├── inteceptors				
│   │   │     │    ├─ ...     	
│   │   │     │
│   │   ├── components
│   │   │     ├── paciente				
│   │   │     │    ├─ pacintes.controller.js
│   │	│     │	   ├─ pacintes.html
│   │	│     │	   ├─ pacinte.edit.controller.js
│   │	│     │	   ├─ pacinte.edit.html
│   │   │     ├── partials				
│   │   │     │    ├─ ...
│   │   │     ├── services				
│   │   │     │    ├─ pacinte.service.js
│   │   │     │
│   │   ├── layout
│   │   │     ├── header				
│   │   │     │    ├─ header.directive.js
│   │	│     │	   ├─ header.directive.html
│   │   │     ├── siderbar				
│   │   │     │    ├─ ...
│   │   │     │
│   │   ├── app.module.js
│   │   ├── app.route.js
│   ├── content
│   │   ├── css
│   │   │     ├── main.css				    	
│   │   ├── img
│   │   │     ├── ...
│   │   │     │
│   ├── vendors
│   │   ├── angular
│   │   │     ├── ...				    	
│   │   ├── jquery
│   │   │     ├── ...
│   │   │
│   │   │
├── index.html
```

REQUISITOS
===============
* JDK 8
* Tomcat 8

USO
===============
 * clone este repositório;
 * Importe o projeto com maven no eclipse;
 * Adicione o tomcat 8;
 * Adicione o `datasource` a seguir no arquivo ```context.xml```  do tomcat:
	```xml
	<Resource name="jdbc/sigapds" auth="Container" type="javax.sql.DataSource"
	driverClassName="org.postgresql.Driver" url="jdbc:postgresql://localhost:5432/NOME-BD"
	username="SEU USER" password="SUA SENHA" maxTotal="20" maxIdle="10"
	maxWaitMillis="-1" />```
##### Isso fará com que o servidor gerencie a conexão com o Banco de Dados e não a aplicação.

 * Inicie o tomcat com as configurações do projeto disponiveis em `/configuracao/application.properties`,
 pode ser feito adicionando o seguinte argumento na execução do tomcat: <br>
 ``` -Dspring.config.location="DIRETORIO DO PROJETO/configuracao/application.properties" ```.
 #####  Isso fará com que as configuraçes do spring possam ser acessadas e alteradas independente de onde a aplicação esteja, isso irá trazer facilidades na transição do projeto para o ambiente de testes e/ou de produção.
 ##### Caso prefira deixar dentro do projeto adicione o application.properties dentro do módulo api em ```src/resources```.
 
 PONTOS IMPORTANTES
===============
##### Lembre de adicionar a lib do drive do postgres dentro do tomcat na pasta ```lib```.
 
