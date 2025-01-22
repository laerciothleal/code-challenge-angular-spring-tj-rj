package com.backend.controller.v1.response;

import java.time.LocalDateTime;
import java.util.Collection;

public record ApiErrorResponse(
        Integer status,
        LocalDateTime timestamp,
        String error,
        String message,
        Collection<String> validationErrors
) {
}
