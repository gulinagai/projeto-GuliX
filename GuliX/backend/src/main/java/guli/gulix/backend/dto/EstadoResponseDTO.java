package guli.gulix.backend.dto;

import jakarta.validation.constraints.Size;

public record EstadoResponseDTO(

        String nome,
        String sigla

) {
}
