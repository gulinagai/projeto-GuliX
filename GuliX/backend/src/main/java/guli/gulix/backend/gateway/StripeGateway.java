package guli.gulix.backend.gateway;

import com.stripe.StripeClient;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import guli.gulix.backend.entity.Pagamento;
import guli.gulix.backend.entity.enums.MetodoPagamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.RoundingMode;

@Component
@RequiredArgsConstructor
public class StripeGateway {

    private final StripeClient stripeClient;

    public ResultadoCheckout criarCheckout(Pagamento pagamento) {

        long valorFinalEmCentavos = pagamento.getValorFinal()
                .movePointRight(2)
                .setScale(0, RoundingMode.HALF_UP)
                .longValueExact();

        SessionCreateParams.LineItem lineItem =
                SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(
                                SessionCreateParams.LineItem.PriceData
                                        .builder()
                                        .setCurrency("brl")
                                        .setUnitAmount(valorFinalEmCentavos)
                                        .setProductData(
                                                SessionCreateParams
                                                        .LineItem
                                                        .PriceData
                                                        .ProductData
                                                        .builder()
                                                        .setName("Pedido GuliX")
                                                        .build()
                                        )
                                        .build()
                        )
                        .build();

        SessionCreateParams sessionParams =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .addPaymentMethodType(
                                obterPaymentMethodType(
                                        pagamento.getMetodoPagamento()
                                )
                        )

                        .setSuccessUrl(
                                "http://localhost:8080/pagamento/sucesso"
                        )
                        .setCancelUrl(
                                "http://localhost:8080/pagamento/cancelado"
                        )
                        .putMetadata(
                                "pagamento_id",
                                pagamento.getId().toString()
                        )
                        .setPaymentIntentData(
                                SessionCreateParams.PaymentIntentData.builder()
                                        .putMetadata(
                                                "pagamento_id",
                                                pagamento.getId().toString()
                                        )
                                        .build()
                        )
                        .addLineItem(lineItem)
                        .build();

        try {

            Session session = stripeClient.v1()
                    .checkout()
                    .sessions()
                    .create(sessionParams);

            return new ResultadoCheckout(
                    session.getUrl(),
                    session.getId(),
                    session.getPaymentIntent()
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Erro ao criar Checkout Session no Stripe",
                    e
            );
        }
    }


    private SessionCreateParams.PaymentMethodType obterPaymentMethodType(MetodoPagamento metodoPagamento) {

        return switch (metodoPagamento) {

            case CARTAO_CREDITO -> SessionCreateParams.PaymentMethodType.CARD;

            case PIX -> SessionCreateParams.PaymentMethodType.PIX;

            case BOLETO -> SessionCreateParams.PaymentMethodType.BOLETO;
        };

    }
}