package com.backend.controller.v1.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record RelatorioResponse(
        String autorNome,
        String livroAssuntos,
        String livroTitulo,
        String livroEditora,
        Integer livroEdicao,
        String livroAnoPublicacao,
        BigDecimal livroValor
) {

}
