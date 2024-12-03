### CRUD com Angular 17 e Java/Spring Boot 
Esse é a pasta raíz dos dois projetos, entrar em cada projeto para ver suas respectivas configurações.

#### Executar frontend e backend juntos usando docker compose.
```
docker-compose build 
```
```
docker-compose up
```

#### Spring Boot vai usar a porta `8080` e o Angular a porta `8081`.

frontend `http://localhost:8081/livro`

backend `http://localhost:8080/swagger-ui/index.html`

h2 console `http://localhost:8080/h2/login.jsp`
  - JDBC URL:  jdbc:h2:~/test
  - User Name: crud
  - Password:  crud
