### CRUD com Angular 17 e Java/Spring Boot 
Esse é a pasta raíz dos dois projetos, entrar em cada projeto para ver suas respectivas configurações.

#### Executar frontend e backend juntos usando docker compose.
```
docker-compose build 
```
```
docker-compose up
```

<img src="project-run.gif" alt="gif" width="800" height="500">

#### Spring Boot vai usar a porta `8080` e o Angular a porta `8081`.

frontend `http://localhost:8081/user`

backend `http://localhost:8080/swagger-ui/index.html`

h2 console `http://localhost:8080/h2/login.jsp`

