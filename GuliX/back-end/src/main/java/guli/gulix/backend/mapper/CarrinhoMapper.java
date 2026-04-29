package guli.gulix.backend.mapper;

import guli.gulix.backend.dto.CarrinhoResponseDTO;
import guli.gulix.backend.entity.Carrinho;

public interface CarrinhoMapper {
    CarrinhoResponseDTO toDTO(Carrinho carrinho);
}
