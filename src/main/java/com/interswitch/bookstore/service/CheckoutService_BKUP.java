//package com.interswitch.bookstore.service;
//
//import com.interswitch.bookstore.exceptions.NotFoundException;
//import com.interswitch.bookstore.exceptions.PaymentException;
//import com.interswitch.bookstore.model.Cart;
//import com.interswitch.bookstore.model.Checkout;
//import com.interswitch.bookstore.model.PaymentMethod;
//import com.interswitch.bookstore.model.PurchaseHistory;
//import com.interswitch.bookstore.repository.CartRepository;
//import com.interswitch.bookstore.repository.CheckoutRepository;
//import com.interswitch.bookstore.repository.PurchaseHistoryRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//public class CheckoutServiceBKUP {
//
//    @Autowired
//    private CheckoutRepository checkoutRepository;
//    @Autowired
//    private CartRepository cartRepository;
//    @Autowired
//    private PurchaseHistoryRepository purchaseHistoryRepository;
//
//    @Autowired
//    private PaymentService paymentService;
//
//
//    @Transactional
//    public Checkout processCheckout(Long cartId, PaymentMethod paymentMethod) {
//        Cart cart = cartRepository.findById(cartId)
//                .orElseThrow(() -> new NotFoundException("Cart not found with ID: " + cartId));
//
//        if (cart.getCartItems().isEmpty()) {
//            throw new PaymentException("Cannot checkout an empty cart.");
//        }
//
//        boolean paymentSuccessful = simulatePayment(paymentMethod);
//        if (!paymentSuccessful) {
//            throw new PaymentException("Payment failed using " + paymentMethod);
//        }
//
//        Checkout checkout = createCheckout(cart, paymentMethod);
//
//        PurchaseHistory purchaseHistory = purchaseHistoryRepository.findById(cartId)
//                .orElseGet(() -> createNewPurchaseHistory(cart));
//
//        checkout.setPurchaseHistory(purchaseHistory);
//        purchaseHistory.getCheckouts().add(checkout);
//
//        purchaseHistoryRepository.save(purchaseHistory);
//        return checkoutRepository.save(checkout);
//    }
//
//    private Checkout createCheckout(Cart cart, PaymentMethod paymentMethod) {
//        Checkout checkout = new Checkout();
//        checkout.setCart(cart);
//        checkout.setPaymentMethod(paymentMethod);
//        checkout.setPaymentSuccessful(true);
//        return checkout;
//    }
//
//    private PurchaseHistory createNewPurchaseHistory(Cart cart) {
//        PurchaseHistory purchaseHistory = new PurchaseHistory();
//        purchaseHistory.setUser(cart.getUser());
//        return purchaseHistory;
//    }
//
//
//    private boolean simulatePayment(PaymentMethod paymentMethod) {
//        if (paymentService.processPayment(paymentMethod.toString()).contains("Successful")) {
//            return Math.random() < 0.5;
//        }
//        return false;
//    }
//
//
////    private boolean simulatePayment(PaymentMethod paymentMethod) {
////
////        return Math.random() < 5000;
////    }
//}
