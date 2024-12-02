package com.backend.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AutorRequest(
        @NotBlank(message = "O nome do autor não pode ser vazio ou nulo.")
        @Size(max = 40, message = "O nome do autor não pode ter mais de 40 caracteres.")
        String nome
) {
}
