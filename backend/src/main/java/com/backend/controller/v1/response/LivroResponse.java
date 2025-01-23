package com.backend.controller.v1.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record LivroResponse(
        Integer codL,
        String titulo,
        String editora,
        Integer edicao,
        String anoPublicacao,
        BigDecimal valor,
        List<AutorResponse> autores,
        List<AssuntoResponse> assuntos
) {
}