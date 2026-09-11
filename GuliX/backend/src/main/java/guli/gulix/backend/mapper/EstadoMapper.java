package guli.gulix.backend.mapper;

import guli.gulix.backend.dto.EstadoCreateDTO;
import guli.gulix.backend.dto.EstadoResponseDTO;
import guli.gulix.backend.dto.EstadoUpdateDTO;
import guli.gulix.backend.entity.Estado;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EstadoMapper {

    EstadoResponseDTO toDTO(Estado estado);

    Estado toEntity(EstadoCreateDTO estadoCreateDTO);

    void updateFromDto(EstadoUpdateDTO estadoUpdateDTO, @MappingTarget Estado estado);
    
}
