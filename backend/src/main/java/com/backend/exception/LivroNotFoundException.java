package com.backend.exception;

public class AutorNotFoundException extends RuntimeException {

    public AutorNotFoundException(String autor) {
        super(String.format("Autor com o nome '%s' não foi encontrado no sistema.", autor));
    }

    public AutorNotFoundException(Integer id) {
        super(String.format("Autor com o id '%s' não foi encontrado no sistema.", id));

    }
}
