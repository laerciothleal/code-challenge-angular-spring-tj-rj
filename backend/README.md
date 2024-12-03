## Spring Boot Application

Aplicação backend de CRUD
É desenvolvido em Java 17 e SpringFramework. 

### Dependências 
- `spring-boot-starter`: O iniciador principal que inclui suporte para configuração automática, registro e configuração YAML.
- `spring-boot-starter-web`: Constrói aplicativos da web, incluindo RESTful, usando Spring MVC. Usa Tomcat como contêiner incorporado padrão.
- `spring-boot-starter-data-jpa`: Fornece suporte JPA e Spring Data JPA para o aplicativo.
- `spring-boot-starter-test`: Fornece bibliotecas de teste, incluindo JUnit, e Mockito.
- `spring-boot-starter-validation`: Fornece bibliotecas de validação

### Banco de dados e ORM
- `Banco de dados H2`: um banco de dados leve e integrado, perfeito para desenvolvimento e teste.
- `Liquibase`: uma biblioteca de gerenciamento de alterações de esquema de banco de dados que ajuda a rastrear, versionar e implantar alterações de banco de dados.

### Documentação da API
- `swagger-annotations`: Anotações para documentar sua API em um formato legível por máquina.
- `springdoc-openapi-starter-webmvc-ui`: Integra OpenAPI 3 com Spring Boot para gerar documentação de API interativa.

### Serviços de utilidade pública
- `Lombok`: uma biblioteca que ajuda a reduzir o código clichê gerando getters, setters e outros métodos comuns em tempo de compilação.
- `jackson-datatype-jdk8`: Suporte para tipos de dados Java 8 em Jackson.


### Aplicação usando Java-17 e SpringFramework
Este projeto é uma aplicação CRUD (Criar, Ler, Atualizar, Excluir) abrangente construída com Angular 17 para o frontend e Java com o Spring Framework para o backend.
Ele demonstra como consumir APIs REST, exibir dados e realizar várias operações em dados de usuários.

### Funcionalidades

- Gestão de Assunto
- Gestão de Autor
- Gestão de Livro
- Relatório de livros por Autor

### Executar apenas o backend, caso deseje
`mvn spring-boot:run`. Usar o link `http://localhost:8081/livro`

### Swagger
Acessar a URL `http://localhost:8080/swagger-ui/index.html`

#### Acessar Console Banco de dados H2
H2 console `http://localhost:8080/h2/login.jsp`




