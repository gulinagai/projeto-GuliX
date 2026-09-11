package guli.gulix.backend.dto;

import java.math.BigDecimal;

public record EmpresaResponseDTO(
        Integer id,
        String razaoSocial,
        String nomeFantasia,
        String cnpj,
        BigDecimal valorPorKmFrete
) {
}