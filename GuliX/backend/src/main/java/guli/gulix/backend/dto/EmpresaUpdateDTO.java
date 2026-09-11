package guli.gulix.backend.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record EmpresaUpdateDTO(

        @Size(max = 150)
        String razaoSocial,

        @Size(max = 100)
        String nomeFantasia,

        @Pattern(
                regexp = "\\d{14}",
                message = "CNPJ deve conter 14 dígitos"
        )
        String cnpj,

        @DecimalMin(
                value = "0.00",
                inclusive = true,
                message = "Valor por km deve ser maior ou igual a zero"
        )
        BigDecimal valorPorKmFrete
) {
}