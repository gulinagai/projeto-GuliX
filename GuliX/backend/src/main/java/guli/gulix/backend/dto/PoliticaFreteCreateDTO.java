package guli.gulix.backend.dto;

import guli.gulix.backend.entity.Estado;

import java.math.BigDecimal;

public record PoliticaFreteCreateDTO(
        Integer id,
        Estado estado,
        BigDecimal valorBase,
        Boolean ativo
) {
}
