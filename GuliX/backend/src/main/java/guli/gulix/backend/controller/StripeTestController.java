package guli.gulix.backend.controller;

import guli.gulix.backend.gateway.StripeGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teste/stripe")
@RequiredArgsConstructor
public class StripeTestController {

    private final StripeGateway stripeGateway;

    @GetMapping("/checkout")
    public String criarCheckout() throws Exception {
        return stripeGateway.criarCheckout();
    }
}