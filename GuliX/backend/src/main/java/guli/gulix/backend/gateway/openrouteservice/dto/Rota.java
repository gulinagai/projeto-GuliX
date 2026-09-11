package guli.gulix.backend.gateway.openrouteservice.dto;

import java.math.BigDecimal;

public record Rota(
        BigDecimal distanciaMetros,
        BigDecimal duracaoSegundos
) {
}
