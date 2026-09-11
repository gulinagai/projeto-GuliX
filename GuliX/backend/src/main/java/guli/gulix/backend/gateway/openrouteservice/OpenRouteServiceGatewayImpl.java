package guli.gulix.backend.gateway.openrouteservice;

import guli.gulix.backend.gateway.openrouteservice.dto.*;
import guli.gulix.backend.geographic.Coordenada;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.math.BigDecimal;
import java.util.List;

@Component
public class OpenRouteServiceGatewayImpl implements OpenRouteServiceGateway {

    private static final Logger log =
            LoggerFactory.getLogger(OpenRouteServiceGatewayImpl.class);

    private final RestClient restClient;
    private final String apiKey;

    public OpenRouteServiceGatewayImpl(
            RestClient restClient,
            @Value("${open.route.service.key}") String apiKey
    ) {
        this.restClient = restClient;
        this.apiKey = apiKey;
    }

    @Override
    public Coordenada snap(Coordenada coordenada) {

        SnapRequestDTO request = new SnapRequestDTO(
                List.of(List.of(
                        coordenada.longitude(),
                        coordenada.latitude()
                )),
                5000
        );

        SnapResponseDTO response = restClient.post()
                .uri("/openrouteservice/v2/snap/driving-car/json")
                .header("Authorization", apiKey)
                .body(request)
                .retrieve()
                .body(SnapResponseDTO.class);

        log.info("Resposta do OpenRouteService: {}", response);

        List<Double> location = response.locations()
                .getFirst()
                .location();

        return new Coordenada(
                location.get(1),
                location.get(0)
        );
    }

    @Override
    public Rota calcularRota(
            Coordenada origem,
            Coordenada destino
    ) {

        DirectionsRequestDTO request = new DirectionsRequestDTO(
                List.of(
                        List.of(
                                origem.longitude(),
                                origem.latitude()
                        ),
                        List.of(
                                destino.longitude(),
                                destino.latitude()
                        )
                )
        );

        DirectionsResponseDTO response = restClient.post()
                .uri("/openrouteservice/v2/directions/driving-car/json")
                .header("Authorization", apiKey)
                .body(request)
                .retrieve()
                .body(DirectionsResponseDTO.class);

        DirectionsResponseDTO.SummaryDTO summary =
                response.routes()
                        .getFirst()
                        .summary();

        return new Rota(
                BigDecimal.valueOf(summary.distance()),
                BigDecimal.valueOf(summary.duration())
        );
    }

}
