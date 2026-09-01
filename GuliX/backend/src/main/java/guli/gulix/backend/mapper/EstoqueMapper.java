package guli.gulix.backend.mapper;

import guli.gulix.backend.dto.EstoqueRequestDTO;
import guli.gulix.backend.dto.EstoqueResponseDTO;
import guli.gulix.backend.entity.Estoque;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EstoqueMapper {

    void updateFromDto(EstoqueRequestDTO dto, @MappingTarget Estoque estoque);

    @Mapping(source = "produto.id", target = "produtoId")
    EstoqueResponseDTO toDTO(Estoque estoque);

}
