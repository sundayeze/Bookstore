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
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private UserRepository userRepository;



    public Cart addToCart(Long userId, Long bookId, int quantity) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book not found with ID: " + bookId));

        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));

        Cart cart = cartRepository.findByUserAndIsPaidFalse(user)
                .orElseGet(() -> createNewCart(user));

        CartItem existingItem = cart.getCartItems().stream()
                .filter(item -> item.getBook().getId().equals(bookId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setBook(book);
            newItem.setQuantity(quantity);
            newItem.setCart(cart);
            newItem.setTotalPrice();
            cart.getCartItems().add(newItem);
            cartItemRepository.save(newItem);
        }

        updateCartTotal(cart);
        return cartRepository.save(cart);
    }



    //   @Transactional
//    public Cart addToCart(Long userId, Long bookId, int quantity) {
//        Book book = bookRepository.findById(bookId)
//                .orElseThrow(() -> new NotFoundException("Book not found with ID: " + bookId));
//
//        AppUser user = userRepository.findById(userId)
//                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));
//
//
//        Cart cart = cartRepository.findByUser(user)
//                .filter(existingCart -> !existingCart.isPaid()) // Use unpaid cart if available
//                .orElseGet(() -> createNewCart(user)); // Create a new cart if all existing ones are paid
//
//
//        CartItem existingItem = cart.getCartItems().stream()
//                .filter(item -> item.getBook().getId().equals(bookId))
//                .findFirst()
//                .orElse(null);
//
//        if (existingItem != null) {
//            existingItem.setQuantity(existingItem.getQuantity() + quantity);
//        } else {
//            CartItem newItem = new CartItem();
//            newItem.setBook(book);
//            newItem.setQuantity(quantity);
//            newItem.setCart(cart);
//            newItem.setTotalPrice();
//            cart.getCartItems().add(newItem);
//            cartItemRepository.save(newItem);
//        }
//
//        updateCartTotal(cart);
//        return cartRepository.save(cart);
//    }

//    public Cart viewCart(Long cartId) {
//        return cartRepository.findByIdAndIsPaidFalse(cartId)
//                .orElseThrow(() -> new NotFoundException("Unpaid cart not found with ID: " + cartId));
//    }


//    public Cart viewCart(Long cartId) {
//
//        return cartRepository.findById(cartId)
//                .orElseThrow(() -> new NotFoundException("Cart not found with ID: " + cartId));
//    }

    public Cart viewCart(Long userId) {
        return cartRepository.findByUserId(userId)
                .map(cart -> {
                    if (cart.isPaid()) {
                        throw new NotFoundException("No unpaid cart available");
                    }
                    return cart;
                })
                .orElseThrow(() -> new NotFoundException("No Cart found for User"));
    }


//    public Cart findByIdAndUserId(Long cartId, Long userId) {
//        return cartRepository.findByIdAndUserId(cartId, userId)
//                .orElseThrow(() -> new NotFoundException("Cart not found for user ID: " + userId));
//    }


    private Cart createNewCart(AppUser user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setCartItems(new ArrayList<>());
        cart.setTotalPrice(0.0);
        cart.setPaid(false);
        return cartRepository.save(cart);
    }

    private void updateCartTotal(Cart cart) {
        double totalPrice = cart.getCartItems()
                .stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
        cart.setTotalPrice(totalPrice);
    }
}
