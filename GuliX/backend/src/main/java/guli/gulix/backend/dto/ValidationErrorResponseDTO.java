package guli.gulix.backend.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ValidationErrorResponseDTO(
        LocalDateTime timestap,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> fields
) {
}
