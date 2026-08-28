package guli.gulix.backend.gateway;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResultadoCheckout {

    private final String checkoutUrl;
    private final String gatewayCheckoutId;
    private final String gatewayPaymentId;

}
