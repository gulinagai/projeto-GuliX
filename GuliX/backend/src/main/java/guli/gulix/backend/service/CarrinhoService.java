package guli.gulix.backend.service;

import guli.gulix.backend.dto.CarrinhoResponseDTO;
import guli.gulix.backend.dto.ItemCarrinhoRequestDTO;
import guli.gulix.backend.dto.ItemCarrinhoUpdateDTO;
import guli.gulix.backend.entity.Carrinho;

import java.math.BigDecimal;

public interface CarrinhoService {
    CarrinhoResponseDTO buscarCarrinho(Integer usuarioId);

    void adicionarItem(Integer usuarioId, ItemCarrinhoRequestDTO item);

    void atualizarQuantidade(Integer usuarioId, Integer itemId, ItemCarrinhoUpdateDTO dto);

    void removerItem(Integer usuarioId, Integer itemId);

    BigDecimal calcularTotal(Carrinho carrinho);
}
