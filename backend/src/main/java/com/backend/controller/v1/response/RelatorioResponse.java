package com.backend.controller.v1.response;

import java.math.BigDecimal;

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
