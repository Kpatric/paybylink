package com.api.paybylink;

import com.api.paybylink.controllers.PaymentController;
import com.api.paybylink.services.PaymentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    @Test
    public void testGetPaymentDetails() throws Exception {
        // Given
        String billHash = "123";
        String paymentChannel = "paybill";
        Object expectedResponse = new Object();

        when(paymentService.getPayments(eq(payRequest),eq(billHash), eq(paymentChannel))).thenReturn(expectedResponse);

        // When
        Object actualResponse = paymentController.getPaymentDetails(billHash, paymentChannel);

        // Then
        verify(paymentService).getPayments(eq(billHash), eq(paymentChannel));
         assertEquals(expectedResponse, actualResponse);
    }
}

