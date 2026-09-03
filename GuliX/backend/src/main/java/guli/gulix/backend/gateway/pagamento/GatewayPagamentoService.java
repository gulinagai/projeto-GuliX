package guli.gulix.backend.gateway.pagamento;

import guli.gulix.backend.entity.Pagamento;

public interface GatewayPagamentoService {

    ResultadoCheckout criarCheckout(Pagamento pagamento);

}
