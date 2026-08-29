package guli.gulix.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record EstoqueRequestInventarioDTO(

        @NotNull
        @PositiveOrZero
        Integer quantidade

) {

}
