package com.interswitch.bookstore;

import com.interswitch.bookstore.exceptions.NotFoundException;
import com.interswitch.bookstore.model.*;
import com.interswitch.bookstore.repository.*;
import com.interswitch.bookstore.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CartService cartService;

    private AppUser user;
    private Book book;
    private Cart cart;
    private CartItem cartItem;

    @BeforeEach
    void setUp() {
        user = new AppUser();
        user.setId(1L);
        user.setName("John Doe");

        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setPrice(100.0);

        cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setQuantity(1);
        cartItem.setTotalPrice();

        cart = new Cart();
        cart.setId(1L);
        cart.setUser(user);
        cart.setCartItems(new ArrayList<>(List.of(cartItem)));
        cart.setTotalPrice(100.0);
        cart.setPaid(false);
    }

    @Test
    void addToCart_ShouldAddBookToCart_WhenCartExists() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        when(cartRepository.findByUserAndIsPaidFalse(user)).thenReturn(Optional.of(cart));

        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart updatedCart = cartService.addToCart(1L, 1L, 1);

        assertNotNull(updatedCart);
        assertEquals(2, updatedCart.getCartItems().get(0).getQuantity());
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void addToCart_ShouldCreateNewCart_WhenNoUnpaidCartExists() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        when(cartRepository.findByUserAndIsPaidFalse(user)).thenReturn(Optional.empty());

        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart updatedCart = cartService.addToCart(1L, 1L, 1);

        assertNotNull(updatedCart);
        assertEquals(1, updatedCart.getCartItems().size());
        verify(cartRepository, times(2)).save(any(Cart.class));
    }

    @Test
    void addToCart_ShouldThrowNotFoundException_WhenBookNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> cartService.addToCart(1L, 1L, 1));

        assertEquals("Book not found with ID: 1", exception.getMessage());
        verify(cartRepository, never()).save(any());
    }

    @Test
    void addToCart_ShouldThrowNotFoundException_WhenUserNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> cartService.addToCart(1L, 1L, 1));

        assertEquals("User not found with ID: 1", exception.getMessage());
        verify(cartRepository, never()).save(any());
    }

    @Test
    void viewCart_ShouldReturnCart_WhenCartExists() {
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));

        Cart retrievedCart = cartService.viewCart(1L);

        assertNotNull(retrievedCart);
        assertEquals(1L, retrievedCart.getId());
    }

    @Test
    void viewCart_ShouldThrowNotFoundException_WhenCartNotFound() {
        when(cartRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> cartService.viewCart(1L));

        assertEquals("Cart not found with ID: 1", exception.getMessage());
    }

}
