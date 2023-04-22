package com.api.paybylink.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
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
