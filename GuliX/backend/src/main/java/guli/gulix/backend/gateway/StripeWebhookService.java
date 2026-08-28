package guli.gulix.backend.gateway;

import com.stripe.model.Event;

public interface StripeWebhookService {

    void processar(Event event);
}
