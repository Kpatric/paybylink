package com.api.paybylink.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
    @JsonIgnore
    private String originatorMSISDN;
    private String payerClientCode;
    private String payerClientName;
    private String checkoutType;
    private String countryCode;
    private String requestOrigin;
    private String msisdn;
    private String language;
    private String accountNumber;
    private String invoiceNumber;
    private String currencyCode;
    private double amount;
    private String description;
    private int billingServiceID;
    private int billHash;
    private String paymentChannel;
    private String paymentCode;

}
