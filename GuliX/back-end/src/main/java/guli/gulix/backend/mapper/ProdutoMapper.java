package guli.gulix.backend.mapper;

import guli.gulix.backend.dto.ProdutoCreateDTO;
import guli.gulix.backend.dto.ProdutoResponseDTO;
import guli.gulix.backend.entity.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    // DTO para Entity
    @Mapping(target = "categoria", ignore = true)
    @Mapping(target = "marca", ignore = true)
    Produto toEntity(ProdutoCreateDTO dto);


    // Entity para DTO
    @Mapping(source = "categoria.id", target = "categoriaId")
    @Mapping(source = "marca.id", target = "marcaId")
    ProdutoResponseDTO toDTO(Produto produto);

}
