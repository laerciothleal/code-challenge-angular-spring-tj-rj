package com.backend.exception;

public class LivroNotFoundException extends ResourceNotFoundException {

    public LivroNotFoundException(Integer id) {
        super(String.format("Livro com o id '%s' não foi encontrado no sistema.", id));
    }
}
