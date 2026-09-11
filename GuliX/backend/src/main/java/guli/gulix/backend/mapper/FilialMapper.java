package guli.gulix.backend.mapper;

import guli.gulix.backend.dto.FilialResponseDTO;
import guli.gulix.backend.dto.FilialUpdateDTO;
import guli.gulix.backend.entity.Filial;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FilialMapper {
    FilialResponseDTO toDTO(Filial filial);

    void updateFromDto(FilialUpdateDTO filialUpdateDTO, @MappingTarget Filial filial);
}
