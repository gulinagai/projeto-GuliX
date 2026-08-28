package guli.gulix.backend.gateway;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhooks/stripe")
public class StripeWebhookController {

    private final String webhookSecret;
    private final StripeWebhookService stripeWebhookService;

    public StripeWebhookController(
            @Value("${stripe.webhook.secret}") String webhookSecret,
            StripeWebhookService stripeWebhookService
    ) {
        this.webhookSecret = webhookSecret;
        this.stripeWebhookService = stripeWebhookService;
    }

    @PostMapping
    public ResponseEntity<Void> receberWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String signature
    ) {

        try {

            Event event = Webhook.constructEvent(
                    payload,
                    signature,
                    webhookSecret
            );

            stripeWebhookService.processar(event);

            return ResponseEntity.ok().build();

        } catch (SignatureVerificationException e) {

            return ResponseEntity.badRequest().build();
        }
    }
}