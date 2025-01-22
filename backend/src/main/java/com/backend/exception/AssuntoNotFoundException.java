package com.backend.exception;

public class AssuntoNotFoundException extends RuntimeException {

    public AssuntoNotFoundException(String itemName) {
        super(String.format("Assunto com o nome '%s' não foi encontrado no sistema.", itemName));
    }

    public AssuntoNotFoundException(Long id) {
        super(String.format("Assunto com id '%s' não foi encontrada no sistema.", id));
    }
}
