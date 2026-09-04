package guli.gulix.backend.gateway.openrouteservice.dto;

import java.util.List;

public record DirectionsResponseDTO(
        List<RouteDTO> routes
) {

    public record RouteDTO(
            SummaryDTO summary
    ) {}

    public record SummaryDTO(
            Double distance,
            Double duration
    ) {}
}