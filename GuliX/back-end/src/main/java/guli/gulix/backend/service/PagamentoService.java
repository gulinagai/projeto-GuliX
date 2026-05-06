package guli.gulix.backend.service;

import guli.gulix.backend.dto.PagamentoRequestDTO;
import guli.gulix.backend.dto.PagamentoResponseDTO;
import guli.gulix.backend.entity.Usuario;

public interface PagamentoService {
    PagamentoResponseDTO criarPagamento(PagamentoRequestDTO dto, Usuario usuario);

    PagamentoResponseDTO getPagamentoById(Integer pagamentoId, Usuario usuario);

    PagamentoResponseDTO confirmarPagamento(Integer pagamentoId, Usuario usuario);
}
