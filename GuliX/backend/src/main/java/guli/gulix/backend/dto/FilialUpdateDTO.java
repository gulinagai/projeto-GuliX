package guli.gulix.backend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FilialUpdateDTO(

        @NotBlank
        @Size(max = 100)
        String nome,

        @Valid
        EnderecoFilialDTO endereco,

        Boolean ativo
) {
}