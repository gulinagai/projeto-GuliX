package guli.gulix.backend.gateway.pagamento;

import com.stripe.model.Event;

public interface StripeWebhookService {

    void processar(Event event);
}
