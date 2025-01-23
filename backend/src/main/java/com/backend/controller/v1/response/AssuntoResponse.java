package com.backend.controller.v1.response;

import lombok.Builder;

@Builder
public record AssuntoResponse(

        Integer codigoAs,
        String descricao
) {

}
