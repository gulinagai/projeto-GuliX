package guli.gulix.backend.mapper;

import guli.gulix.backend.dto.ItemCarrinhoResponseDTO;
import guli.gulix.backend.entity.ItemCarrinho;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ProdutoMapper.class)
public interface ItemCarrinhoMapper {
    @Mapping(source = "produto.id", target = "produtoId")
    @Mapping(source = "produto.nome", target = "nomeProduto")
    @Mapping(source = "produto.preco", target = "preco")
    ItemCarrinhoResponseDTO toDTO(ItemCarrinho item);
}