package guli.gulix.backend.mapper;


import guli.gulix.backend.dto.CategoriaRequestDTO;
import guli.gulix.backend.dto.CategoriaResponseDTO;
import guli.gulix.backend.entity.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    // para CREATE:

    Categoria toEntity(CategoriaRequestDTO dto);

    // para UPDATE:

    void updateFromDto(CategoriaRequestDTO dto, @MappingTarget Categoria categoria);

    // para Response:

    CategoriaResponseDTO toDTO(Categoria categoria);
}
