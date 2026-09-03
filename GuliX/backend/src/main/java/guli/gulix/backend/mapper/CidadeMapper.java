package guli.gulix.backend.mapper;

import guli.gulix.backend.dto.CidadeCreateDTO;
import guli.gulix.backend.dto.CidadeResponseDTO;
import guli.gulix.backend.dto.CidadeUpdateDTO;
import guli.gulix.backend.entity.Cidade;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CidadeMapper {

    CidadeResponseDTO toDTO(Cidade cidade);

    Cidade toEntity(CidadeCreateDTO cidadeCreateDTO);

    void updateFromDto(CidadeUpdateDTO cidadeUpdateDTO, @MappingTarget Cidade cidade);

}
