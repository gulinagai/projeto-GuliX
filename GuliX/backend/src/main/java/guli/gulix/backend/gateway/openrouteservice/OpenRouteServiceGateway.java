package guli.gulix.backend.gateway.openrouteservice;

import guli.gulix.backend.gateway.openrouteservice.dto.Rota;
import guli.gulix.backend.geographic.Coordenada;

public interface OpenRouteServiceGateway {

    Coordenada snap(Coordenada coordenada);

    Rota calcularRota(Coordenada origem, Coordenada destino);

}
