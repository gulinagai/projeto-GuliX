package guli.gulix.backend.service;

import guli.gulix.backend.dto.PedidoResponseDTO;
import guli.gulix.backend.dto.PedidoUpdateStatusDTO;
import guli.gulix.backend.entity.Pedido;
import guli.gulix.backend.entity.Usuario;
import guli.gulix.backend.entity.enums.Role;
import guli.gulix.backend.entity.enums.StatusPedido;
import guli.gulix.backend.exception.RecursoNaoEncontradoException;
import guli.gulix.backend.exception.RegraNegocioException;
import guli.gulix.backend.mapper.PedidoMapper;
import guli.gulix.backend.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;

    // USER

    @Override
    public List<PedidoResponseDTO> getListPedidos(Usuario usuario) {

        List<Pedido> pedidos = pedidoRepository.findByUsuario(usuario);

        return pedidos.stream().map(pedidoMapper::toDTO).toList();
    }

    @Override
    public PedidoResponseDTO getPedidoById(Usuario usuario, Integer pedidoId) {

        Pedido pedido;

        switch (usuario.getRole()) {
            case Role.ROLE_ADMIN:
                pedido = pedidoRepository.findById(pedidoId)
                        .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado"));
                break;

            case Role.ROLE_USER:
                pedido = pedidoRepository.findByIdAndUsuario(pedidoId, usuario)
                        .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado"));
                break;

            default:
                throw new RegraNegocioException("Role não autorizada");
        }


        return pedidoMapper.toDTO(pedido);
    }

    @Override
    public PedidoResponseDTO createNewPedido(Usuario usuario) {

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setStatusPedido(StatusPedido.PENDENTE);

        Pedido saved = pedidoRepository.save(pedido);

        return pedidoMapper.toDTO(saved);
    }

    @Override
    public PedidoResponseDTO cancelPedido(Integer pedidoId, Usuario usuario) {

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado"));

        validatePedidoOwner(pedido, usuario);


        if (StatusPedido.CANCELADO.equals(pedido.getStatusPedido())) {
            throw new RegraNegocioException("Pedido já está cancelado");
        }

        pedido.setStatusPedido(StatusPedido.CANCELADO);

        return pedidoMapper.toDTO(pedido);
    }




    // ADMIN

    @Override
    public List<PedidoResponseDTO> getAllPedidos() {
        return pedidoRepository.findAll().stream().map(pedidoMapper::toDTO).toList();
    }

    @Override
    public PedidoResponseDTO updateStatusPedido(Integer pedidoId, PedidoUpdateStatusDTO dto) {

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado"));


        if (dto.getStatus() == null) {
            throw new RegraNegocioException("Status é obrigatório");
        }

        pedido.setStatusPedido(dto.getStatus());

        return pedidoMapper.toDTO(pedido);
    }


    private void validatePedidoOwner(Pedido pedido, Usuario usuario) {
        if (!pedido.getUsuario().getId().equals(usuario.getId())) {
            throw new RegraNegocioException("Acesso negado");
        }
    }

}
