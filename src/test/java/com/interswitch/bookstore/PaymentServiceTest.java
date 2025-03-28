package com.interswitch.bookstore;

import com.interswitch.bookstore.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceTest {

    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        paymentService = new PaymentService();
    }

    @Test
    void testProcessPayment_Web() {
        String result = paymentService.processPayment("Web");
        assertEquals("Web Payment Successful", result);
    }

    @Test
    void testProcessPayment_USSD() {
        String result = paymentService.processPayment("USSD");
        assertEquals("USSD Payment Successful", result);
    }

    @Test
    void testProcessPayment_Transfer() {
        String result = paymentService.processPayment("Transfer");
        assertEquals("Bank Transfer Successful", result);
    }

    @Test
    void testProcessPayment_InvalidMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            paymentService.processPayment("Cash");
        });

        assertEquals("Invalid payment method", exception.getMessage());
    }
}
