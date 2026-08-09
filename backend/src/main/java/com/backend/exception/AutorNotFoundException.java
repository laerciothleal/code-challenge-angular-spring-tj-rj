package com.backend.exception;

public class AutorNotFoundException extends ResourceNotFoundException {

    public AutorNotFoundException(Integer id) {
        super(String.format("Autor com o id '%s' não foi encontrado no sistema.", id));
    }
}
