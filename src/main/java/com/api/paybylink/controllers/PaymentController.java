package com.api.paybylink.controllers;

import com.api.paybylink.models.Payment;
import com.api.paybylink.services.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URISyntaxException;
import java.util.Map;

@RestController
@RequestMapping("/paybylink")
public class PaymentController {


    @Autowired
    private PaymentService paymentService;


    @GetMapping("/pay")
    public Object getPaymentDetails(@RequestBody Payment payRequest,@RequestParam("billHash") String billHash,
                                  @RequestParam("paymentChannel") String paymentChannel) throws JSONException {
        return paymentService.getPayments(payRequest,billHash, paymentChannel);
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
