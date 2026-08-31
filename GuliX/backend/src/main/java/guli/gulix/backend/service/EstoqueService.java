package guli.gulix.backend.service;

import guli.gulix.backend.dto.EstoqueRequestDTO;
import guli.gulix.backend.dto.EstoqueRequestInventarioDTO;
import guli.gulix.backend.dto.EstoqueResponseDTO;

import java.util.List;

public interface EstoqueService {
    List<EstoqueResponseDTO> getAllEstoque();

    EstoqueResponseDTO getEstoqueByProdutoId(Integer produtoId);

    EstoqueResponseDTO adicionarEstoque(Integer produtoId, EstoqueRequestDTO estoqueRequest);

    EstoqueResponseDTO removerEstoque(Integer produtoId, EstoqueRequestDTO estoqueRequest);

    EstoqueResponseDTO realizarInventario(Integer produtoId, EstoqueRequestInventarioDTO estoqueRequest);

    Integer getQuantidadeDisponivel(Integer produtoId);

    void setQuantidadeReservada(Integer produtoId, Integer quantidadeAReservar, String operacao);
}
