package guli.gulix.backend.service;

import guli.gulix.backend.geographic.Coordenada;

import java.math.BigDecimal;

public interface CalculoFreteService {

    BigDecimal calcularFrete(Coordenada coordenada, String estado);

}
