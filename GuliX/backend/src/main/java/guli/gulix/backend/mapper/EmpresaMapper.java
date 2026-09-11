package guli.gulix.backend.mapper;

import guli.gulix.backend.dto.EmpresaResponseDTO;
import guli.gulix.backend.dto.EmpresaUpdateDTO;
import guli.gulix.backend.entity.Empresa;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmpresaMapper {
    EmpresaResponseDTO toDTO(Empresa empresa);

    void updateFromDto(EmpresaUpdateDTO empresaUpdateDTO, @MappingTarget Empresa empresa);
}
