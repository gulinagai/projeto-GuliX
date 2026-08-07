package guli.gulix.backend.service;

import guli.gulix.backend.dto.PagamentoRequestDTO;
import guli.gulix.backend.dto.PagamentoResponseDTO;
import guli.gulix.backend.dto.PedidoCreateDTO;
import guli.gulix.backend.entity.Pedido;
import guli.gulix.backend.entity.Usuario;

public interface PagamentoService {
    PagamentoResponseDTO criarPagamento(Pedido pedido, PedidoCreateDTO pedidoCreateDTO);

    PagamentoResponseDTO getPagamentoById(Integer pagamentoId, Usuario usuario);

    PagamentoResponseDTO confirmarPagamento(Integer pagamentoId, Usuario usuario);
}
