package com.backend.controller.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record LivroRequest(
        @NotBlank(message = "O título não pode ser vazio ou nulo.")
        String titulo,

        @NotBlank(message = "A editora não pode ser vazia ou nula.")
        String editora,

        @NotNull(message = "A edição não pode ser nula.")
        @Min(value = 1, message = "A edição deve ser maior que zero.")
        Integer edicao,

        @NotBlank(message = "O ano de publicação não pode ser vazio ou nulo.")
        @Size(min = 4, max = 4, message = "O ano de publicação deve ter 4 dígitos.")
        String anoPublicacao,

        @NotNull(message = "O valor não pode ser nulo.")
        @Min(value = 0, message = "O valor deve ser maior ou igual a zero.")
        BigDecimal valor,
        @NotNull(message = "O valor não pode ser nulo.")
        List<Integer> autoresIds,  // IDs dos autores associados
        @NotNull(message = "O valor não pode ser nulo.")
        List<Integer> assuntosIds // IDs dos assuntos associados
) {
}