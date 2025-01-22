package com.backend.exception;

public class LivroNotFoundException extends RuntimeException {

    public LivroNotFoundException(String livro) {
        super(String.format("Livro com o nome '%s' não foi encontrado no sistema.", livro));
    }

    public LivroNotFoundException(Integer id) {
        super(String.format("Livro com o id '%s' não foi encontrado no sistema.", id));

    }
}
