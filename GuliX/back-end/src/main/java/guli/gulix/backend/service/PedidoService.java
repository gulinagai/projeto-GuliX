package guli.gulix.backend.service;

import guli.gulix.backend.dto.PedidoResponseDTO;
import guli.gulix.backend.dto.PedidoUpdateStatusDTO;
import guli.gulix.backend.entity.Usuario;

import java.util.List;

public interface PedidoService {
    List<PedidoResponseDTO> getListPedidos(Usuario usuario);

    PedidoResponseDTO getPedidoById(Usuario usuario, Integer pedidoId);

    PedidoResponseDTO createNewPedido(Usuario usuario);

    PedidoResponseDTO cancelPedido(Integer pedidoId, Usuario usuario);

    List<PedidoResponseDTO> getAllPedidos();

    PedidoResponseDTO updateStatusPedido(Integer pedidoId, PedidoUpdateStatusDTO dto);
}
