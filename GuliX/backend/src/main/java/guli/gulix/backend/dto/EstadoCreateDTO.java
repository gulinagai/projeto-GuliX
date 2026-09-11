package guli.gulix.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EstadoCreateDTO(

        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 100, message = "O nome deve possuir no máximo 100 caracteres")
        String nome,

        @NotBlank(message = "A sigla é obrigatória")
        @Size(min = 2, max = 2, message = "A sigla deve possuir exatamente 2 caracteres")
        String sigla

) {
}
