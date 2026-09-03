package guli.gulix.backend.gateway.openrouteservice.dto;

import java.util.List;

public record SnapResponseDTO(
        List<LocationDTO> locations
) {

    public record LocationDTO(
            List<Double> location
    ) {
    }
}