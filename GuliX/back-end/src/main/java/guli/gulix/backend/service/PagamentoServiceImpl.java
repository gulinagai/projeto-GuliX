package guli.gulix.backend.service;

import guli.gulix.backend.dto.PagamentoRequestDTO;
import guli.gulix.backend.dto.PagamentoResponseDTO;
import guli.gulix.backend.entity.Pedido;
import guli.gulix.backend.entity.Usuario;
import guli.gulix.backend.mapper.PagamentoMapper;
import guli.gulix.backend.repository.PagamentoRepository;
import guli.gulix.backend.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PagamentoServiceImpl implements PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final PedidoRepository pedidoRepository;
    private final PagamentoMapper pagamentoMapper;

    @Override
    public PagamentoResponseDTO criarPagamento(PagamentoRequestDTO dto, Usuario usuario) {

        Pedido pedido = findPedidoOrThrow(dto.getPedidoId());

        validateAccess(pedido, usuario);

        // regra: não permitir pagamento duplicado
        if (pedido.getPagamento() != null) {
            throw new RegraNegocioException("Pedido já possui pagamento");
        }

        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(pedido);
        pagamento.setMetodoPagamento(dto.getMetodoPagamento());
        pagamento.setValor(dto.getValor());
        pagamento.setStatusPagamento(StatusPagamento.PENDENTE);

        Pagamento saved = pagamentoRepository.save(pagamento);

        return pagamentoMapper.toDTO(saved);
    }


    @Override
    public PagamentoResponseDTO getPagamentoById(Integer pagamentoId, Usuario usuario) {

        Pagamento pagamento = findPagamentoOrThrow(pagamentoId);

        //  valida acesso
        validateAccess(pagamento.getPedido(), usuario);

        return pagamentoMapper.toDTO(pagamento);
    }


    @Override
    public PagamentoResponseDTO confirmarPagamento(Integer pagamentoId, Usuario usuario) {

        Pagamento pagamento = findPagamentoOrThrow(pagamentoId);

        Pedido pedido = pagamento.getPedido();

        // 🔒 valida acesso
        validateAccess(pedido, usuario);

        if (pagamento.getStatusPagamento() == StatusPagamento.CONFIRMADO) {
            throw new RegraNegocioException("Pagamento já está confirmado");
        }

        pagamento.setStatusPagamento(StatusPagamento.CONFIRMADO);

        // 🔥 integração com pedido
        pedido.setStatusPedido(StatusPedido.PAGO);

        return pagamentoMapper.toDTO(pagamento);
    }


    private Pagamento findPagamentoOrThrow(Integer pagamentoId) {
        return pagamentoRepository.findById(pagamentoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pagamento não encontrado"));
    }

    private Pedido findPedidoOrThrow(Integer pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado"));
    }

    private void validateAccess(Pedido pedido, Usuario usuario) {


        if (usuario.getRole().name().equals("ADMIN")) {
            return;
        }

        if (!pedido.getUsuario().getId().equals(usuario.getId())) {
            throw new RegraNegocioException("Acesso negado");
        }
    }
}
