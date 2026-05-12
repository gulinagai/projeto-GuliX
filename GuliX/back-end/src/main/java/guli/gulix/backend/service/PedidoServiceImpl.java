package guli.gulix.backend.service;

import guli.gulix.backend.dto.PedidoCreateDTO;
import guli.gulix.backend.dto.PedidoResponseDTO;
import guli.gulix.backend.dto.PedidoUpdateStatusDTO;
import guli.gulix.backend.entity.*;
import guli.gulix.backend.entity.enums.Role;
import guli.gulix.backend.entity.enums.StatusPagamento;
import guli.gulix.backend.entity.enums.StatusPedido;
import guli.gulix.backend.exception.RecursoNaoEncontradoException;
import guli.gulix.backend.exception.RegraNegocioException;
import guli.gulix.backend.mapper.PedidoMapper;
import guli.gulix.backend.repository.CarrinhoRepository;
import guli.gulix.backend.repository.EnderecoRepository;
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
    private final CarrinhoRepository carrinhoRepository;
    private final EnderecoRepository enderecoRepository;
    private final CarrinhoService carrinhoService;
    private final PagamentoService pagamentoService;

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
    public PedidoResponseDTO createNewPedido(Usuario usuario, PedidoCreateDTO pedidoCreateDTO) {

        Pedido newPedido = new Pedido();

        // buscar carrinho

        Carrinho carrinho = carrinhoRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Carrinho não encontrado"));

        // buscar para ver se o endereço passado para entrega existe no banco

        Endereco enderecoExiste = enderecoRepository.findById(pedidoCreateDTO.getEnderecoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Endereço não encontrado"));

        // Mapping Pedido

        newPedido.setUsuario(carrinho.getUsuario());
        newPedido.setStatusPedido(StatusPedido.PENDENTE);
        newPedido.setTotal(carrinhoService.calcularTotal(carrinho));

        EnderecoEntrega enderecoEntrega = new EnderecoEntrega();

        enderecoEntrega.setRua(enderecoExiste.getRua());
        enderecoEntrega.setNumero(enderecoExiste.getNumero());
        enderecoEntrega.setCep(enderecoExiste.getCep());
        enderecoEntrega.setCidade(enderecoExiste.getCidade());
        enderecoEntrega.setEstado(enderecoExiste.getEstado());

        newPedido.setEnderecoEntrega(enderecoEntrega);


        List<ItemPedido> itensPedido = carrinho.getItens().stream().map(
                itemCarrinho -> {
                    ItemPedido itemPedido = new ItemPedido();

                    itemPedido.setProduto(itemCarrinho.getProduto());
                    itemPedido.setQuantidade(itemCarrinho.getQuantidade());
                    itemPedido.setPrecoUnitario(itemCarrinho.getProduto().getPreco());
                    itemPedido.setPedido(newPedido);

                    return itemPedido;
                }
        ).toList();



        // Mapping Pagamento

        pagamentoService.criarPagamento(newPedido, pedidoCreateDTO);




        Pedido saved = pedidoRepository.save(newPedido);

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
