package guli.gulix.backend.mapper;

import guli.gulix.backend.dto.ProdutoCreateDTO;
import guli.gulix.backend.dto.ProdutoResponseDTO;
import guli.gulix.backend.dto.ProdutoUpdateDTO;
import guli.gulix.backend.entity.Produto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    // DTO para Entity (Create)
    @Mapping(target = "categoria", ignore = true)
    @Mapping(target = "marca", ignore = true)
    Produto toEntity(ProdutoCreateDTO dto);


    // Entity para DTO (Response)
    @Mapping(source = "categoria.id", target = "categoriaId")
    @Mapping(source = "marca.id", target = "marcaId")
    ProdutoResponseDTO toDTO(Produto produto);

    // Update (PATCH/PUT)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "categoria", ignore = true)
    @Mapping(target = "marca", ignore = true)
    void updateFromDto(ProdutoUpdateDTO dto, @MappingTarget Produto produto);

}
