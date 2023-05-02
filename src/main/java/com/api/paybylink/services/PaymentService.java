package com.api.paybylink.services;

import com.api.paybylink.configs.HttpClient;
import com.api.paybylink.models.Payment;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;


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
    public Object getPayments(Payment payRequest,String billHash, String paymentChannel) throws JSONException {
        String url = baseUrl + getEndPoint + "?billHash=" + billHash + "&paymentChannel=" + paymentChannel;

        var results = httpClient.getRestTemplate().getForObject(url, Object.class);
        Map<String, Object> map = (Map<String, Object>) results;
        JSONObject jsonObject = new JSONObject(map);

        var paymentRequest = parsePayment(payRequest,jsonObject);


        return postPayment(paymentRequest) ;
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


    public Payment parsePayment(Payment payment,JSONObject jsonObject) throws JSONException {
        return Payment.builder()
                .originatorMSISDN(jsonObject.getJSONObject("bill").getString("msisdn"))
                .payerClientCode(payment.getPayerClientCode())
                .payerClientName(payment.getPayerClientName())
                .checkoutType(payment.getCheckoutType())
                .countryCode(jsonObject.getJSONObject("biller").getString("country"))
                .requestOrigin(payment.getRequestOrigin())
                .msisdn(jsonObject.getJSONObject("bill").getString("msisdn"))
                .language(payment.getLanguage())
                .accountNumber(jsonObject.getJSONObject("bill").getString("accountNumber"))
                .invoiceNumber("")
                .currencyCode(jsonObject.getJSONObject("bill").getString("currencyCode"))
                .amount(jsonObject.getJSONObject("bill").getDouble("dueAmount"))
                .description(payment.getDescription())
                .billingServiceID(jsonObject.getJSONObject("billingServiceResponse").getInt("billingServiceID"))
                .billHash(jsonObject.getJSONObject("bill").getInt("billID"))
                .paymentChannel(payment.getPaymentChannel())
                .paymentCode(payment.getPaymentCode())
                .build();
    }

}
