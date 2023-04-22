package com.api.paybylink.controllers;

import com.api.paybylink.models.Payment;
import com.api.paybylink.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/paybylink")
public class PaymentController {


    @Autowired
    private PaymentService paymentService;


    @GetMapping("/pay")
    public Object getPaymentDetails(@RequestParam("billHash") String billHash,
                                  @RequestParam("paymentChannel") String paymentChannel) {
        return paymentService.getPayments(billHash, paymentChannel);
    }

    // non blocking http get request using reactive programming
    @GetMapping("/payments")
    public Mono<Object> getPaymentDetailsFlux(@RequestParam("billHash") String billHash,
                                          @RequestParam("paymentChannel") String paymentChannel) {
        return paymentService.getPaymentsFlux(billHash, paymentChannel);
    }

    @PostMapping("/charge")
    public Object makePayment(@RequestBody Payment payment){
        return paymentService.postPayment(payment);

    }
}
