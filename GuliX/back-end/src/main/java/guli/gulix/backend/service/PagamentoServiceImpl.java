package guli.gulix.backend.service;

import guli.gulix.backend.dto.PagamentoResponseDTO;
import guli.gulix.backend.dto.PedidoCreateDTO;
import guli.gulix.backend.entity.Pagamento;
import guli.gulix.backend.entity.Pedido;
import guli.gulix.backend.entity.Usuario;
import guli.gulix.backend.entity.enums.MetodoPagamento;
import guli.gulix.backend.entity.enums.StatusPagamento;
import guli.gulix.backend.entity.enums.StatusPedido;
import guli.gulix.backend.exception.RecursoNaoEncontradoException;
import guli.gulix.backend.exception.RegraNegocioException;
import guli.gulix.backend.mapper.PagamentoMapper;
import guli.gulix.backend.repository.PagamentoRepository;
import guli.gulix.backend.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
@Transactional
public class PagamentoServiceImpl implements PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final PedidoRepository pedidoRepository;
    private final PagamentoMapper pagamentoMapper;

    @Override
    public PagamentoResponseDTO criarPagamento(Pedido pedido, PedidoCreateDTO pedidoCreateDTO) {

        Pagamento newPagamento = new Pagamento();


        newPagamento.setMetodoPagamento(pedidoCreateDTO.getMetodoPagamento());
        newPagamento.setStatusPagamento(StatusPagamento.PENDENTE);
        newPagamento.setValorOriginal(pedido.getTotal());

        BigDecimal desconto = calculaDesconto(newPagamento.getValorOriginal(), newPagamento.getMetodoPagamento());

        newPagamento.setDesconto(desconto);

        if(newPagamento.getMetodoPagamento() == MetodoPagamento.BOLETO || newPagamento.getMetodoPagamento() == MetodoPagamento.PIX) {
            newPagamento.setValorFinal(calculaValorFinalPixBoleto(newPagamento.getValorOriginal(), newPagamento.getDesconto()));
        }

        newPagamento.setNumeroParcelas(pedidoCreateDTO.getNumeroParcelas());

        if(newPagamento.getMetodoPagamento() == MetodoPagamento.CARTAO_CREDITO) {
            newPagamento.setValorParcela(calculaValorParcelas(newPagamento.getValorFinal(), newPagamento.getNumeroParcelas()));
        } else if (newPagamento.getMetodoPagamento() == MetodoPagamento.BOLETO || newPagamento.getMetodoPagamento() == MetodoPagamento.PIX) {
            newPagamento.setValorParcela(null);
            newPagamento.setNumeroParcelas(null);
        }


        if(newPagamento.getMetodoPagamento() == MetodoPagamento.CARTAO_CREDITO) {
            newPagamento.setValorFinal(calculaValorFinalCartaoDeCredito(newPagamento.getValorParcela(), newPagamento.getNumeroParcelas()));
        }

        newPagamento.setPedido(pedido);

        Pagamento saved = pagamentoRepository.save(newPagamento);

        return pagamentoMapper.toDTO(saved);
    }



    private BigDecimal calculaValorParcelas(BigDecimal valorFinal, Integer numeroParcelas) {
        if(numeroParcelas > 0 && numeroParcelas <= 6) {
            BigDecimal valorParcela = valorFinal.divide(
                    BigDecimal.valueOf(numeroParcelas),
                    2,
                    RoundingMode.HALF_UP
            );
            return valorParcela;
        } else if(numeroParcelas > 6 && numeroParcelas <= 48) {
            BigDecimal juros =
                    BigDecimal.valueOf(0.02); // 2% de juros

            BigDecimal valorComJuros =
                    valorFinal.multiply(
                            BigDecimal.ONE.add(juros)
                    );
            return valorComJuros.divide(
                    BigDecimal.valueOf(numeroParcelas),
                    2,
                    RoundingMode.HALF_UP);

        } else if(numeroParcelas > 48) {
            throw new RegraNegocioException("Quantidade de parcelas não permitida.");
        } else {
            throw new RegraNegocioException("Quantidade de parcelas inválidas.");
        }
    }

    private BigDecimal calculaValorFinalPixBoleto(BigDecimal valorOriginal, BigDecimal desconto) {
        return valorOriginal.subtract(desconto);
    }

    private BigDecimal calculaValorFinalCartaoDeCredito(BigDecimal valorParcela, Integer numeroParcelas) {
        return valorParcela.multiply(BigDecimal.valueOf(numeroParcelas));
    }

    private BigDecimal calculaDesconto(BigDecimal valorOriginal, MetodoPagamento metodoPagamento) {

        if(metodoPagamento == MetodoPagamento.BOLETO || metodoPagamento == MetodoPagamento.PIX) {
            BigDecimal descontoPorcentagem =  BigDecimal.valueOf(10);
            BigDecimal desconto = (valorOriginal.multiply(descontoPorcentagem).divide(
                    BigDecimal.valueOf(100),
                    2,
                    RoundingMode.HALF_UP));

                    return desconto;
        } else if(metodoPagamento == MetodoPagamento.CARTAO_CREDITO) {
            return BigDecimal.ZERO;
        } else {
            throw new RegraNegocioException("Método de Pagamento inválido");
        }


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

        //  valida acesso
        validateAccess(pedido, usuario);

        if (pagamento.getStatusPagamento() == StatusPagamento.CONFIRMADO) {
            throw new RegraNegocioException("Pagamento já está confirmado");
        }

        pagamento.setStatusPagamento(StatusPagamento.CONFIRMADO);

        //  integração com pedido
        pedido.setStatusPedido(StatusPedido.APROVADO);

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
