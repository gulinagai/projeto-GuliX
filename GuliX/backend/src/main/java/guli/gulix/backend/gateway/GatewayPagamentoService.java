package guli.gulix.backend.gateway;

import guli.gulix.backend.entity.Pagamento;

public interface GatewayPagamentoService {

    ResultadoCheckout criarCheckout(Pagamento pagamento);

}
