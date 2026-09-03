package guli.gulix.backend.dto;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CidadeUpdateDTO(

        @Size(max = 150, message = "O nome deve possuir no máximo 150 caracteres")
        String nome,

        Integer estadoId,

        @DecimalMin(value = "-90.0", message = "A latitude deve estar entre -90 e 90")
        @DecimalMax(value = "90.0", message = "A latitude deve estar entre -90 e 90")
        BigDecimal latitudeCentral,

        @DecimalMin(value = "-180.0", message = "A longitude deve estar entre -180 e 180")
        @DecimalMax(value = "180.0", message = "A longitude deve estar entre -180 e 180")
        BigDecimal longitudeCentral

) {
}
