package com.interswitch.bookstore.controller;

import com.interswitch.bookstore.exceptions.NotFoundException;
import com.interswitch.bookstore.exceptions.PaymentException;
import com.interswitch.bookstore.model.Checkout;
import com.interswitch.bookstore.model.Genre;
import com.interswitch.bookstore.model.PaymentMethod;
import com.interswitch.bookstore.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @PostMapping("/{cartId}")
    public ResponseEntity<?> processCheckout(@PathVariable Long cartId, @RequestParam String paymentMethod) {

        PaymentMethod payment = null;

        if (paymentMethod != null) {
            try {
                payment = PaymentMethod.valueOf(paymentMethod.toUpperCase().trim());


            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest()
                        .body("Invalid Payment method: " + paymentMethod);
            }
        }

        try {
            checkoutService.processCheckout(cartId, payment);
            return ResponseEntity.ok("Checkout successful! Payment method: " + paymentMethod);
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (PaymentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred: " + e.getMessage());
        }
    }
}
