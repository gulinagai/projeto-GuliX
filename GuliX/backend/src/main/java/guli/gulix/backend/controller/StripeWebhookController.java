package guli.gulix.backend.controller;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhooks/stripe")
public class StripeWebhookController {

    private final String webhookSecret;

    public StripeWebhookController(
            @Value("${stripe.webhook.secret}") String webhookSecret
    ) {
        this.webhookSecret = webhookSecret;
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

            switch (event.getType()) {
                case "checkout.session.completed" -> {
                    System.out.println("Checkout concluído!");
                }

                case "payment_intent.succeeded" -> {
                    System.out.println("Pagamento confirmado!");
                }

                default -> {
                    System.out.println("Evento não tratado: " + event.getType());
                }
            }



            System.out.println("Evento Stripe recebido: " + event.getType());

            return ResponseEntity.ok().build();

        } catch (SignatureVerificationException e) {
            return ResponseEntity.badRequest().build();
        }

    }

}
