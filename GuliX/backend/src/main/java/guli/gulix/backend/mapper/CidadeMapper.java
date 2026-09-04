package guli.gulix.backend.mapper;

import guli.gulix.backend.dto.CidadeCreateDTO;
import guli.gulix.backend.dto.CidadeResponseDTO;
import guli.gulix.backend.dto.CidadeUpdateDTO;
import guli.gulix.backend.entity.Cidade;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CidadeMapper {

    @Mapping(source = "estado.id", target = "estadoId")
    CidadeResponseDTO toDTO(Cidade cidade);

    @Mapping(target = "estado", ignore = true)
    Cidade toEntity(CidadeCreateDTO cidadeCreateDTO);

    @Mapping(target = "estado", ignore = true)
    void updateFromDto(CidadeUpdateDTO cidadeUpdateDTO, @MappingTarget Cidade cidade);

}
