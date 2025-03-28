package com.interswitch.bookstore;

import com.interswitch.bookstore.exceptions.NotFoundException;
import com.interswitch.bookstore.exceptions.PaymentException;
import com.interswitch.bookstore.model.*;
import com.interswitch.bookstore.repository.*;
import com.interswitch.bookstore.service.CheckoutService;
import com.interswitch.bookstore.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckoutServiceTest {

    @Mock
    private CheckoutRepository checkoutRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private PurchaseHistoryRepository purchaseHistoryRepository;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private CheckoutService checkoutService;

    private Cart cart;
    private AppUser user;
    private Checkout checkout;
    private PurchaseHistory purchaseHistory;

    @BeforeEach
    void setUp() {
        user = new AppUser();
        user.setId(1L);

        cart = new Cart();
        cart.setId(1L);
        cart.setUser(user);
        cart.setPaid(false);

        checkout = new Checkout();
        checkout.setCart(cart);
        checkout.setPaymentMethod(PaymentMethod.TRANSFER);
        checkout.setPaymentSuccessful(true);

        purchaseHistory = new PurchaseHistory();
        purchaseHistory.setUser(user);
    }

    @Test
    void processCheckout_Successful() {
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
        when(checkoutRepository.findByCart(cart)).thenReturn(Optional.empty());
        when(purchaseHistoryRepository.findByUser(user)).thenReturn(Optional.of(purchaseHistory));
        when(paymentService.processPayment(anyString())).thenReturn("Successful");
        when(checkoutRepository.save(any(Checkout.class))).thenReturn(checkout);

        Checkout result = checkoutService.processCheckout(1L, PaymentMethod.TRANSFER);

        assertNotNull(result);
        assertEquals(cart, result.getCart());
        assertTrue(result.isPaymentSuccessful());
        assertEquals(PaymentMethod.TRANSFER, result.getPaymentMethod());

        verify(cartRepository, times(1)).save(cart);
        verify(checkoutRepository, times(1)).save(any(Checkout.class));
    }

    @Test
    void processCheckout_CartNotFound_ShouldThrowException() {
        when(cartRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                checkoutService.processCheckout(1L, PaymentMethod.TRANSFER));

        assertEquals("Cart not found", exception.getMessage());
    }

    @Test
    void processCheckout_CartAlreadyCheckedOut_ShouldThrowException() {
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
        when(checkoutRepository.findByCart(cart)).thenReturn(Optional.of(checkout));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                checkoutService.processCheckout(1L, PaymentMethod.TRANSFER));

        assertEquals("This cart has already been checked out.", exception.getMessage());
    }

    @Test
    void processCheckout_CartAlreadyPaid_ShouldThrowException() {
        cart.setPaid(true);
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                checkoutService.processCheckout(1L, PaymentMethod.TRANSFER));

        assertEquals("This cart has already been paid for.", exception.getMessage());
    }

    @Test
    void processCheckout_PaymentFails_ShouldThrowException() {
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
        when(checkoutRepository.findByCart(cart)).thenReturn(Optional.empty());
        when(purchaseHistoryRepository.findByUser(user)).thenReturn(Optional.of(purchaseHistory));
        when(paymentService.processPayment(anyString())).thenReturn("Failed");

        PaymentException exception = assertThrows(PaymentException.class, () ->
                checkoutService.processCheckout(1L, PaymentMethod.TRANSFER));

        assertEquals("Payment failed using TRANSFER", exception.getMessage());
    }

    @Test
    void processCheckout_CreatesNewPurchaseHistory_WhenNotFound() {
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
        when(checkoutRepository.findByCart(cart)).thenReturn(Optional.empty());
        when(purchaseHistoryRepository.findByUser(user)).thenReturn(Optional.empty());
        when(paymentService.processPayment(anyString())).thenReturn("Successful");
        when(purchaseHistoryRepository.save(any(PurchaseHistory.class))).thenReturn(purchaseHistory);
        when(checkoutRepository.save(any(Checkout.class))).thenReturn(checkout);

        Checkout result = checkoutService.processCheckout(1L, PaymentMethod.TRANSFER);

        assertNotNull(result);
        assertEquals(cart, result.getCart());
        assertEquals(PaymentMethod.TRANSFER, result.getPaymentMethod());
        assertTrue(result.isPaymentSuccessful());

        verify(purchaseHistoryRepository, times(1)).save(any(PurchaseHistory.class));
        verify(cartRepository, times(1)).save(cart);
        verify(checkoutRepository, times(1)).save(any(Checkout.class));
    }
}
