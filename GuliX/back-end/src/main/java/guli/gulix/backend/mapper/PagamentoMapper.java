package guli.gulix.backend.mapper;

import guli.gulix.backend.dto.PagamentoResponseDTO;
import guli.gulix.backend.entity.Pagamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PagamentoMapper {

    @Mapping(source = "pedido.id", target = "pedidoId")
    PagamentoResponseDTO toDTO(Pagamento pagamento);
}
