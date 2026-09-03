package guli.gulix.backend.gateway.pagamento;

import guli.gulix.backend.entity.Pagamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GatewayPagamentoServiceImpl implements GatewayPagamentoService {

    private final StripeGateway stripeGateway;

    @Override
    public ResultadoCheckout criarCheckout(Pagamento pagamento) {

        return stripeGateway.criarCheckout(pagamento);
    }
}