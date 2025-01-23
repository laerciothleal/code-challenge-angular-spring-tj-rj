package com.backend.controller.v1.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Collection;

@Builder
public record ApiErrorResponse(
        Integer status,
        LocalDateTime timestamp,
        String error,
        String message,
        Collection<String> validationErrors
) {
}
