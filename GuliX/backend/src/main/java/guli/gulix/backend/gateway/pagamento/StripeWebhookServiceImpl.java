package guli.gulix.backend.gateway.pagamento;

import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import guli.gulix.backend.entity.ItemPedido;
import guli.gulix.backend.entity.Pagamento;
import guli.gulix.backend.entity.Pedido;
import guli.gulix.backend.entity.enums.StatusPagamento;
import guli.gulix.backend.entity.enums.StatusPedido;
import guli.gulix.backend.exception.RecursoNaoEncontradoException;
import guli.gulix.backend.repository.PagamentoRepository;
import guli.gulix.backend.service.EstoqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class StripeWebhookServiceImpl implements StripeWebhookService {

    private final PagamentoRepository pagamentoRepository;
    private final EstoqueService estoqueService;

    @Override
    public void processar(Event event) {

        switch (event.getType()) {

            case "checkout.session.completed" ->
                    processarCheckoutSessionCompleted(event);

            case "payment_intent.succeeded" ->
                    processarPaymentIntentSucceeded(event);

            case "payment_intent.payment_failed" ->
                    processarPaymentIntentFailed(event);

            default -> {
                log.info("Evento Stripe não tratado: {}", event.getType());
            }
        }
    }

    private void processarCheckoutSessionCompleted(Event event) {

        Session session = (Session) event
                .getDataObjectDeserializer()
                .getObject()
                .orElseThrow(
                        () -> new IllegalStateException(
                                "Não foi possível desserializar a Checkout Session."
                        )
                );

        String pagamentoId = session
                .getMetadata()
                .get("pagamento_id");

        if (pagamentoId == null) {
            throw new IllegalStateException(
                    "Pagamento não informado na metadata da Checkout Session."
            );
        }

        Pagamento pagamento = pagamentoRepository
                .findById(Integer.valueOf(pagamentoId))
                .orElseThrow(
                        () -> new RecursoNaoEncontradoException(
                                "Pagamento não encontrado."
                        )
                );

        if (!session.getId().equals(pagamento.getGatewayCheckoutId())) {
            throw new IllegalStateException(
                    "A Checkout Session não corresponde ao pagamento."
            );
        }

    }

    private void processarPaymentIntentSucceeded(Event event) {

        PaymentIntent paymentIntent = (PaymentIntent) event
                .getDataObjectDeserializer()
                .getObject()
                .orElseThrow(
                        () -> new IllegalStateException(
                                "Não foi possível desserializar o PaymentIntent."
                        )
                );

        String pagamentoId = paymentIntent
                .getMetadata()
                .get("pagamento_id");


        if (pagamentoId == null) {
            throw new IllegalStateException(
                    "Pagamento não informado na metadata do PaymentIntent."
            );
        }

        Pagamento pagamento = pagamentoRepository
                .findById(Integer.valueOf(pagamentoId))
                .orElseThrow(
                        () -> new RecursoNaoEncontradoException(
                                "Pagamento com id " + pagamentoId + " não encontrado."
                        )
                );

        if (pagamento.getStatusPagamento() == StatusPagamento.CONFIRMADO) {
            return;
        }

        pagamento.setGatewayPaymentId(paymentIntent.getId());

        pagamento.setStatusPagamento(
                StatusPagamento.CONFIRMADO
        );

        Pedido pedido = pagamento.getPedido();

        pedido.setStatusPedido(
                StatusPedido.APROVADO
        );

        for(ItemPedido itemPedido : pedido.getItens()) {
            estoqueService.setQuantidadeReservada(itemPedido.getProduto().getId(), itemPedido.getQuantidade(), "subtracao");
            estoqueService.setQuantidadeTotal(itemPedido.getProduto().getId(), itemPedido.getQuantidade(), "subtracao");
        }

    }

    private void processarPaymentIntentFailed(Event event) {

        PaymentIntent paymentIntent = (PaymentIntent) event
                .getDataObjectDeserializer()
                .getObject()
                .orElseThrow(
                        () -> new IllegalStateException(
                                "Não foi possível desserializar o PaymentIntent."
                        )
                );

        String pagamentoId = paymentIntent
                .getMetadata()
                .get("pagamento_id");


        if (pagamentoId == null) {
            throw new IllegalStateException(
                    "Pagamento não informado na metadata do PaymentIntent."
            );
        }

        Pagamento pagamento = pagamentoRepository
                .findById(Integer.valueOf(pagamentoId))
                .orElseThrow(
                        () -> new RecursoNaoEncontradoException(
                                "Pagamento com id " + pagamentoId + " não encontrado."
                        )
                );

        pagamento.setGatewayPaymentId(paymentIntent.getId());

        pagamento.setStatusPagamento(
                StatusPagamento.RECUSADO
        );
    }
}