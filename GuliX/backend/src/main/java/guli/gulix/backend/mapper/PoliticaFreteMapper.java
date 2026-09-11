package guli.gulix.backend.mapper;

import guli.gulix.backend.dto.PoliticaFreteCreateDTO;
import guli.gulix.backend.dto.PoliticaFreteResponseDTO;
import guli.gulix.backend.dto.PoliticaFreteUpdateDTO;
import guli.gulix.backend.entity.PoliticaFrete;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PoliticaFreteMapper {

    PoliticaFreteResponseDTO toDTO(PoliticaFrete politicaFrete);

    PoliticaFrete toEntity(PoliticaFreteCreateDTO politicaFreteCreateDTO);

    void updateFromDto(PoliticaFreteUpdateDTO politicaFreteUpdateDTO, @MappingTarget PoliticaFrete politicaFrete);
    
}
