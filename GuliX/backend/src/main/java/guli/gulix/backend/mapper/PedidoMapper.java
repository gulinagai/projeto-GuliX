package guli.gulix.backend.mapper;

import guli.gulix.backend.dto.PedidoResponseDTO;
import guli.gulix.backend.entity.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    @Mapping(source = "usuario.id", target = "usuarioId")
    PedidoResponseDTO toDTO(Pedido pedido);
}