package guli.gulix.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EstoqueRequestDTO(

        @NotNull
        @Positive
        Integer quantidade
) {
}
