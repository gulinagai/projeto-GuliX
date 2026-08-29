package guli.gulix.backend.mapper;

import guli.gulix.backend.dto.EstoqueRequestDTO;
import guli.gulix.backend.dto.EstoqueResponseDTO;
import guli.gulix.backend.entity.Estoque;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EstoqueMapper {

    void updateFromDto(EstoqueRequestDTO dto, @MappingTarget Estoque estoque);


    EstoqueResponseDTO toDTO(Estoque estoque);

}
