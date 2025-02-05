# CRUD com Angular 17

Este é o **frontend** de um aplicativo CRUD completo, desenvolvido com Angular 17. O projeto se comunica com um backend em Java utilizando o Spring Framework.

## Descrição do Projeto

Este aplicativo de exemplo permite:
- **Criar, Ler, Atualizar e Deletar** registros (CRUD)
- Consumir APIs REST
- Exibir e modificar dados
- Gerar relatórios detalhados

## Funcionalidades do Frontend

- **Gestão de Assunto:** Gerencie os assuntos relacionados aos livros.
- **Gestão de Autor:** Administre as informações dos autores.
- **Gestão de Livro:** Controle os registros dos livros.
- **Relatório de Livros por Autor:** Visualize relatórios agrupados por autor.

## Como Executar o Frontend

Se você deseja executar apenas o frontend (sem utilizar o `docker-compose`), siga os passos abaixo:

1. **Instale as dependências:**
    ```bash
    npm install
    ```
2. **Inicie o servidor de desenvolvimento:**
    ```bash
    ng serve --port 8081
    ```
3. **Acesse a aplicação:**
    Abra seu navegador e navegue até:
    ```
    http://localhost:8081/autor
    ```

## Estrutura do Projeto

A estrutura do projeto foi organizada para facilitar a manutenção e o desenvolvimento. Uma visão geral da estrutura é:

```
/src
  /app
    /components         # Componentes da aplicação
    /services         # Serviços para integração com APIs REST
    /models         # Modelos de dados
    ...
```

## Considerações Finais

Este projeto demonstra como construir um aplicativo CRUD utilizando Angular 17 para consumir APIs REST e integrar com um backend em Java (Spring Framework).

---

> **Observação:** Este README refere-se exclusivamente ao frontend. Para instruções sobre o backend, consulte o repositório correspondente.