package org.jerry.order.dto;

import java.time.LocalDateTime;
import java.util.Set;

public record ErrorResponse(
        int status,
        String error,
        String message,
        String path,
        LocalDateTime timestamp,
        Set<String> validationErrors
) {
}
