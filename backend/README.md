# Aplicação Backend de CRUD com Spring Boot

## Descrição do Projeto

Este projeto é a aplicação backend de um sistema CRUD (Criar, Ler, Atualizar e Excluir) desenvolvido com Java 17 e Spring Framework. A aplicação fornece APIs REST para gerenciar dados de Assuntos, Autores e Livros, além de gerar relatórios de livros por Autor. Ela foi desenvolvida para integrar com um frontend em Angular 17, demonstrando a comunicação entre ambos por meio de serviços REST.

## Tecnologias Utilizadas

- **Linguagem**: Java 17
- **Framework**: Spring Boot, Spring MVC, Spring Data JPA, Spring Validation
- **Banco de Dados**: H2 (ideal para desenvolvimento e testes)
- **ORM**: Spring Data JPA (Hibernate)
- **Liquibase**: Gerenciamento de alterações de esquema de banco de dados
- **Documentação da API**: Springdoc OpenAPI (Swagger UI)
- **Utilitários**:
    - **Lombok**: Geração automática de getters, setters e outros métodos comuns.
    - **Jackson Datatype JDK8**: Suporte para tipos de dados Java 8 na serialização/deserialização.

## Como Executar o Projeto


### Passos para Execução

1. **Clonar o Repositório**

  ```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio
```

2. **Construir e Executar a Aplicação**

**Via Maven:**

Compile e execute a aplicação:

  ```bash
mvn clean compile
mvn spring-boot:run
```

A aplicação ficará disponível na porta padrão (8080). Para acessar os endpoints, por exemplo, utilize:

  ```
http://localhost:8080/autor
```

**Via Docker (opcional):**

Caso prefira utilizar Docker, crie um arquivo `docker-compose.yml` com o seguinte conteúdo:

  ```yaml
version: '3'
services:
  backend:
    build:
      dockerfile: Dockerfile
    ports:
      - "8080:8080"
    volumes:
      - .m2:/root/.m2
    stdin_open: true
    tty: true
  ```

E, em seguida, execute:

  ```bash
docker-compose up --build
```

3. **Executar Testes**

Para rodar a suíte de testes do projeto:

  ```bash
mvn clean test
```

4. **Acessar a Documentação da API**

Após iniciar a aplicação, acesse a documentação interativa via Swagger:

- [Swagger UI](http://localhost:8080/swagger-ui/index.html)

5. **Acessar o Console do Banco de Dados H2**

Acesse o console do H2 para visualizar e manipular o banco de dados:

- [H2 Console](http://localhost:8080/h2/login.jsp)

As credenciais para acesso estão configuradas na raiz do projeto (consulte o arquivo de propriedades para detalhes).

## Funcionalidades

- **Gestão de Assunto**: Cadastro e atualização dos assuntos relacionados aos livros.
- **Gestão de Autor**: Gerenciamento de informações dos autores.
- **Gestão de Livro**: Controle completo dos registros dos livros.
- **Relatório de Livros por Autor**: Geração de relatórios detalhados, agrupados por autor.

## Observações

Esta aplicação backend foi desenvolvida para funcionar em conjunto com um frontend em Angular 17. Para mais informações sobre o frontend, consulte o repositório correspondente.

