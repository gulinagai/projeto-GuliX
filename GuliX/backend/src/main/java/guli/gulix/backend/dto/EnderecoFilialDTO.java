package guli.gulix.backend.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record EnderecoFilialDTO(

        @NotBlank
        String rua,

        @NotBlank
        String numero,

        @NotBlank
        String cidade,

        @NotBlank
        @Size(min = 2, max = 2)
        String estado,

        @NotBlank
        String cep,

        @DecimalMin("-90.0")
        @DecimalMax("90.0")
        BigDecimal latitude,

        @DecimalMin("-180.0")
        @DecimalMax("180.0")
        BigDecimal longitude
) {
}