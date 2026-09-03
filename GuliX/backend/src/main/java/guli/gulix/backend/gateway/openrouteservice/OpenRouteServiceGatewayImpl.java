package guli.gulix.backend.gateway.openrouteservice;

import guli.gulix.backend.gateway.openrouteservice.dto.SnapRequestDTO;
import guli.gulix.backend.gateway.openrouteservice.dto.SnapResponseDTO;
import guli.gulix.backend.geographic.Coordenada;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class OpenRouteServiceGatewayImpl implements OpenRouteServiceGateway {

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
                300
        );

        SnapResponseDTO response = restClient.post()
                .uri("/ors/v2/snap/driving-car/json")
                .header("Authorization", apiKey)
                .body(request)
                .retrieve()
                .body(SnapResponseDTO.class);

        List<Double> location = response.locations()
                .getFirst()
                .location();

        return new Coordenada(
                location.get(1),
                location.get(0)
        );
    }
}
