package guli.gulix.backend.mapper;


import guli.gulix.backend.dto.EnderecoCreateDTO;
import guli.gulix.backend.dto.EnderecoResponseDTO;
import guli.gulix.backend.dto.EnderecoUpdateDTO;
import guli.gulix.backend.entity.Endereco;
import org.mapstruct.*;

@Mapper(componentModel = "spring")

public interface EnderecoMapper {

    // para CREATE:
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "principal", ignore = true)
    Endereco toEntity(EnderecoCreateDTO dto);

    // para UPDATE:

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "principal", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(EnderecoUpdateDTO dto, @MappingTarget Endereco endereco);

    // para Response:

    EnderecoResponseDTO toDTO(Endereco endereco);

}
