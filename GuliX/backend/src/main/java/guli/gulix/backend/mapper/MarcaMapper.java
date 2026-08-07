package guli.gulix.backend.mapper;


import guli.gulix.backend.dto.MarcaRequestDTO;
import guli.gulix.backend.dto.MarcaResponseDTO;

import guli.gulix.backend.entity.Marca;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface MarcaMapper {

    // para CREATE:

    Marca toEntity(MarcaRequestDTO dto);

    // para UPDATE:

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(MarcaRequestDTO dto, @MappingTarget Marca marca);

    // para Response:

    MarcaResponseDTO toDTO(Marca marca);
}
