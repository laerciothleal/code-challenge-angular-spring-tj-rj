package com.backend.controller.response;

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
