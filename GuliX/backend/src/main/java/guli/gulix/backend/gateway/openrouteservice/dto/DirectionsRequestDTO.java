package guli.gulix.backend.gateway.openrouteservice.dto;

import java.util.List;

public record DirectionsRequestDTO(
        List<List<Double>> coordinates
) {
}
