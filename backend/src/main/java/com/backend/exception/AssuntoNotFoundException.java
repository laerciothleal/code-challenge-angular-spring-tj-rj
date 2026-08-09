package com.backend.exception;

public class AssuntoNotFoundException extends ResourceNotFoundException {

    public AssuntoNotFoundException(Integer id) {
        super(String.format("Assunto com o id '%s' não foi encontrado no sistema.", id));
    }
}
