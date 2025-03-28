package com.interswitch.bookstore.service;

import com.interswitch.bookstore.exceptions.NotFoundException;
import com.interswitch.bookstore.exceptions.PaymentException;
import com.interswitch.bookstore.model.*;
import com.interswitch.bookstore.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    @Autowired
    private CheckoutRepository checkoutRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private PurchaseHistoryRepository purchaseHistoryRepository;
    @Autowired
    private PaymentService paymentService;


    @Transactional
    public Checkout processCheckout(Long cartId, PaymentMethod paymentMethod) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Optional<Checkout> existingCheckout = checkoutRepository.findByCart(cart);
        if (existingCheckout.isPresent()) {
            throw new IllegalStateException("This cart has already been checked out.");
        }

        if (cart.isPaid()) {
            throw new IllegalStateException("This cart has already been paid for.");
        }

        boolean paymentSuccessful = processPayment(paymentMethod);
        if (!paymentSuccessful) {
            throw new PaymentException("Payment failed using " + paymentMethod);
        }

        PurchaseHistory purchaseHistory = purchaseHistoryRepository.findByUser(cart.getUser())
                .orElseGet(() -> {
                    PurchaseHistory newHistory = new PurchaseHistory();
                    newHistory.setUser(cart.getUser());
                    return purchaseHistoryRepository.save(newHistory);
                });

        Checkout checkout = new Checkout();
        checkout.setCart(cart);
        checkout.setPaymentMethod(paymentMethod);
        checkout.setPaymentSuccessful(true);
        checkout.setPurchaseHistory(purchaseHistory);


        cart.setPaid(true);
        cartRepository.save(cart);

        return checkoutRepository.save(checkout);
    }


//    @Transactional
//    public Checkout processCheckout(Long cartId, PaymentMethod paymentMethod) {
//        Cart cart = cartRepository.findById(cartId)
//                .orElseThrow(() -> new RuntimeException("Cart not found"));
//
//        Optional<Checkout> existingCheckout = checkoutRepository.findByCart(cart);
//        if (existingCheckout.isPresent()) {
//            throw new IllegalStateException("This cart has already been checked out.");
//        }
//
//        if (cart.isPaid()) {
//            throw new IllegalStateException("This cart has already been paid for.");
//        }
//
//        PurchaseHistory purchaseHistory = purchaseHistoryRepository.findByUser(cart.getUser())
//                .orElseGet(() -> {
//                    PurchaseHistory newHistory = new PurchaseHistory();
//                    newHistory.setUser(cart.getUser());
//                    return purchaseHistoryRepository.save(newHistory);
//                });
//
//        Checkout checkout = new Checkout();
//        checkout.setCart(cart);
//        checkout.setPaymentMethod(paymentMethod);
//        checkout.setPaymentSuccessful(true);
//        checkout.setPurchaseHistory(purchaseHistory);
//
//        cart.setPaid(true);
//        cartRepository.save(cart);
//
//        return checkoutRepository.save(checkout);
//    }






    private Checkout createCheckout(Cart cart, PaymentMethod paymentMethod) {
        Checkout checkout = new Checkout();
        checkout.setCart(cart);
        checkout.setPaymentMethod(paymentMethod);
        checkout.setPaymentSuccessful(true);
        return checkout;
    }

    private PurchaseHistory createNewPurchaseHistory(Cart cart) {
        PurchaseHistory purchaseHistory = new PurchaseHistory();
        purchaseHistory.setUser(cart.getUser());
        return purchaseHistory;
    }

    private void createNewCart(AppUser user) {
        Cart newCart = new Cart();
        newCart.setUser(user);
        newCart.setCartItems(new ArrayList<>());
        newCart.setTotalPrice(0.0);
        newCart.setPaid(false);
        cartRepository.save(newCart);
    }

        private boolean processPayment(PaymentMethod paymentMethod) {
        if (paymentService.processPayment(paymentMethod.toString()).contains("Successful")) {
            return Math.random() < 0.75;
        }
        return false;
    }

//    private boolean processPayment(PaymentMethod paymentMethod) {
//        return paymentService.processPayment(paymentMethod.toString()).contains("Successful");
//    }


}
