package guli.gulix.backend.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CidadeCreateDTO(

        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 150, message = "O nome deve possuir no máximo 150 caracteres")
        String nome,

        @NotNull(message = "O estado é obrigatório")
        Integer estadoId,

        @NotNull(message = "A latitude central da cidade é obrigatória")
        @DecimalMin(value = "-90.0", message = "A latitude deve estar entre -90 e 90")
        @DecimalMax(value = "90.0", message = "A latitude deve estar entre -90 e 90")
        BigDecimal latitudeCentral,

        @NotNull(message = "A longitude central da cidade é obrigatória")
        @DecimalMin(value = "-180.0", message = "A longitude deve estar entre -180 e 180")
        @DecimalMax(value = "180.0", message = "A longitude deve estar entre -180 e 180")
        BigDecimal longitudeCentral

) {
}
