package guli.gulix.backend.mapper;

import guli.gulix.backend.dto.UsuarioCreateDTO;
import guli.gulix.backend.dto.UsuarioResponseDTO;
import guli.gulix.backend.dto.UsuarioUpdateDTO;
import guli.gulix.backend.entity.Usuario;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "senhaHash", ignore = true)
    Usuario toEntity(UsuarioCreateDTO dto);

    UsuarioResponseDTO toDTO(Usuario usuario);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityfromDTO(UsuarioUpdateDTO dto, @MappingTarget Usuario usuario);
}
