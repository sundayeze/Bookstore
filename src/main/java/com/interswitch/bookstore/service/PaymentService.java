package com.interswitch.bookstore.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    public String processPayment(String method) {
        switch (method.toUpperCase()) {
            case "WEB": return "Web Payment Successful";
            case "USSD": return "USSD Payment Successful";
            case "TRANSFER": return "Bank Transfer Successful";
            default: throw new IllegalArgumentException("Invalid payment method");
        }
    }
}
