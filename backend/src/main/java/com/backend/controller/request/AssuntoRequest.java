package com.backend.controller.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record AssuntoRequest(

        @NotEmpty(message = "A descrição do assunto não pode ser vazia.")
        @Size(max = 20, message = "A descrição do assunto deve ter no máximo 20 caracteres.")
        String descricao
) {
}
