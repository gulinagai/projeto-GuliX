package guli.gulix.backend.gateway.openrouteservice.dto;

import java.util.List;

public record SnapRequestDTO(
        List<List<Double>> locations,
        Integer radius
) {
}
