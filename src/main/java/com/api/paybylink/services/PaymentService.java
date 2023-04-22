package com.api.paybylink.services;

import com.api.paybylink.configs.HttpClient;
import com.api.paybylink.models.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class PaymentService {

    @Value("${getEndPoint}")
    private String getEndPoint;

    @Value("${baseUrl}")
    private String baseUrl;

    @Value("${postEndPoint}")
    private String postEndPoint;

    @Autowired
    private HttpClient httpClient;


    //http request using blocking http request
    public Object getPayments(String billHash, String paymentChannel) {
        String url = baseUrl + getEndPoint + "?billHash=" + billHash + "&paymentChannel=" + paymentChannel;
        return httpClient.getRestTemplate().getForObject(url, Object.class);
    }

    // non blocking post using webflux
    public Mono<Object> getPaymentsFlux(String billHash, String paymentChannel) {
        String url = baseUrl + getEndPoint + "?billHash=" + billHash + "&paymentChannel=" + paymentChannel;
        return httpClient.getWebClientBuilder().build().get()
                .uri(url)
                .retrieve()
                .bodyToMono(Object.class);
    }


    public Object postPayment(Payment payment) {
        String url = baseUrl + postEndPoint;
        return httpClient.getRestTemplate().postForObject(url, payment, Object.class);
    }

    // non blocking post using webflux
    public Mono<Object> postPaymentFlux(Payment payment) {
        String url = baseUrl + postEndPoint;
        return httpClient.getWebClientBuilder().build().post()
                .uri(url)
                .bodyValue(payment)
                .retrieve()
                .bodyToMono(Object.class);
    }

}
