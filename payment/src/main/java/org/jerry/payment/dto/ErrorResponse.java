package org.jerry.payment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ErrorResponse(
        int status,
        String error,
        String message,
        Set<String> validationErrors,
        String path,
        LocalDateTime timestamp
) {
}
