package guli.gulix.backend.dto;

public record AuthResponseDTO(
        String token,
        String refreshToken,
        String type
) {}
