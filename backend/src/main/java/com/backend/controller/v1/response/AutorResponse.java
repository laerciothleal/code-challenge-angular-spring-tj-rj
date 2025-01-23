package com.backend.controller.v1.response;

import lombok.Builder;

@Builder
public record AutorResponse(
        Integer codAu,
        String nome
) {
}
