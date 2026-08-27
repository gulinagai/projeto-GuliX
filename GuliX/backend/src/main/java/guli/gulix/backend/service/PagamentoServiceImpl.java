package guli.gulix.backend.service;

import guli.gulix.backend.dto.PagamentoResponseDTO;
import guli.gulix.backend.dto.PedidoCreateDTO;
import guli.gulix.backend.entity.Pagamento;
import guli.gulix.backend.entity.Pedido;
import guli.gulix.backend.entity.Usuario;
import guli.gulix.backend.entity.enums.GatewayPagamento;
import guli.gulix.backend.entity.enums.MetodoPagamento;
import guli.gulix.backend.entity.enums.StatusPagamento;
import guli.gulix.backend.entity.enums.StatusPedido;
import guli.gulix.backend.exception.RecursoNaoEncontradoException;
import guli.gulix.backend.exception.RegraNegocioException;
import guli.gulix.backend.mapper.PagamentoMapper;
import guli.gulix.backend.repository.PagamentoRepository;
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
    private final PagamentoMapper pagamentoMapper;

    @Override
    public PagamentoResponseDTO criarPagamento(
            Pedido pedido,
            PedidoCreateDTO pedidoCreateDTO
    ) {

        Pagamento pagamento = new Pagamento();

        pagamento.setPedido(pedido);
        pagamento.setMetodoPagamento(
                pedidoCreateDTO.getMetodoPagamento()
        );
        pagamento.setStatusPagamento(StatusPagamento.PENDENTE);

        // Gateway utilizado para processar o pagamento
        pagamento.setGateway(GatewayPagamento.STRIPE);

        // O valor vem do pedido, nunca do cliente
        pagamento.setValorOriginal(pedido.getTotal());

        BigDecimal desconto = calculaDesconto(
                pagamento.getValorOriginal(),
                pagamento.getMetodoPagamento()
        );

        pagamento.setDesconto(desconto);

        // Valor após aplicação do desconto
        BigDecimal valorBase = pagamento.getValorOriginal()
                .subtract(pagamento.getDesconto());

        if (pagamento.getMetodoPagamento()
                == MetodoPagamento.CARTAO_CREDITO) {

            pagamento.setNumeroParcelas(
                    pedidoCreateDTO.getNumeroParcelas()
            );


            pagamento.setValorFinal(
                    calculaValorFinalComJuros(
                            valorBase,
                            pagamento.getNumeroParcelas()
                    )
            );

            pagamento.setValorParcela(
                    calculaValorParcela(
                            pagamento.getValorFinal(),
                            pagamento.getNumeroParcelas()
                    )
            );

        } else {

            pagamento.setNumeroParcelas(null);
            pagamento.setValorParcela(null);

            // PIX/Boleto não possuem juros de parcelamento
            pagamento.setValorFinal(valorBase);
        }

        Pagamento saved = pagamentoRepository.save(pagamento);

        return pagamentoMapper.toDTO(saved);
    }

    private BigDecimal calculaValorFinalComJuros(
            BigDecimal valorBase,
            Integer numeroParcelas
    ) {

        if (numeroParcelas == null) {
            throw new RegraNegocioException(
                    "Quantidade de parcelas é obrigatória para cartão de crédito."
            );
        }

        if (numeroParcelas <= 0) {
            throw new RegraNegocioException(
                    "Quantidade de parcelas inválida."
            );
        }

        // Até 6 parcelas não possuem juros
        if (numeroParcelas <= 6) {
            return valorBase;
        }

        // De 7 até 48 parcelas aplica 2% de juros
        if (numeroParcelas <= 48) {

            BigDecimal juros = BigDecimal.valueOf(0.02);

            return valorBase.multiply(
                    BigDecimal.ONE.add(juros)
            );
        }

        throw new RegraNegocioException(
                "Quantidade de parcelas não permitida."
        );
    }

    private BigDecimal calculaValorParcela(
            BigDecimal valorFinal,
            Integer numeroParcelas
    ) {

        if (numeroParcelas == null) {
            throw new RegraNegocioException(
                    "Quantidade de parcelas é obrigatória para cartão de crédito."
            );
        }

        if (numeroParcelas <= 0) {
            throw new RegraNegocioException(
                    "Quantidade de parcelas inválida."
            );
        }

        return valorFinal.divide(
                BigDecimal.valueOf(numeroParcelas),
                2,
                RoundingMode.HALF_UP
        );
    }

    private BigDecimal calculaDesconto(
            BigDecimal valorOriginal,
            MetodoPagamento metodoPagamento
    ) {

        if (metodoPagamento == MetodoPagamento.BOLETO
                || metodoPagamento == MetodoPagamento.PIX) {

            BigDecimal descontoPorcentagem = BigDecimal.valueOf(10);

            return valorOriginal
                    .multiply(descontoPorcentagem)
                    .divide(
                            BigDecimal.valueOf(100),
                            2,
                            RoundingMode.HALF_UP
                    );
        }

        if (metodoPagamento == MetodoPagamento.CARTAO_CREDITO) {
            return BigDecimal.ZERO;
        }

        throw new RegraNegocioException(
                "Método de Pagamento inválido"
        );
    }

    @Override
    public PagamentoResponseDTO getPagamentoById(
            Integer pagamentoId,
            Usuario usuario
    ) {

        Pagamento pagamento = findPagamentoOrThrow(pagamentoId);

        validateAccess(pagamento.getPedido(), usuario);

        return pagamentoMapper.toDTO(pagamento);
    }

    @Override
    public PagamentoResponseDTO confirmarPagamento(
            Integer pagamentoId,
            Usuario usuario
    ) {

        Pagamento pagamento = findPagamentoOrThrow(pagamentoId);

        Pedido pedido = pagamento.getPedido();

        validateAccess(pedido, usuario);

        if (pagamento.getStatusPagamento()
                == StatusPagamento.CONFIRMADO) {

            throw new RegraNegocioException(
                    "Pagamento já está confirmado"
            );
        }

        pagamento.setStatusPagamento(
                StatusPagamento.CONFIRMADO
        );

        pedido.setStatusPedido(
                StatusPedido.APROVADO
        );

        return pagamentoMapper.toDTO(pagamento);
    }

    private Pagamento findPagamentoOrThrow(Integer pagamentoId) {

        return pagamentoRepository.findById(pagamentoId)
                .orElseThrow(
                        () -> new RecursoNaoEncontradoException(
                                "Pagamento não encontrado"
                        )
                );
    }

    private void validateAccess(
            Pedido pedido,
            Usuario usuario
    ) {

        if (usuario.getRole().name().equals("ADMIN")) {
            return;
        }

        if (!pedido.getUsuario().getId()
                .equals(usuario.getId())) {

            throw new RegraNegocioException(
                    "Acesso negado"
            );
        }
    }
}