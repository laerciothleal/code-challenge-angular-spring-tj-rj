package com.backend.exception;

public class AssuntoNotFoundException extends RuntimeException {

    public AssuntoNotFoundException(String assunto) {
        super(String.format("Assunto com o nome '%s' não foi encontrado no sistema.", assunto));
    }

    public AssuntoNotFoundException(Integer id) {
        super(String.format("Assunto com o id '%s' não foi encontrado no sistema.", id));

    }
}
