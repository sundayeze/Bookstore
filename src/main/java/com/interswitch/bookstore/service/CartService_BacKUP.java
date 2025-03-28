//package com.interswitch.bookstore.service;
//
//import com.interswitch.bookstore.exceptions.NotFoundException;
//import com.interswitch.bookstore.model.AppUser;
//import com.interswitch.bookstore.model.Book;
//import com.interswitch.bookstore.model.Cart;
//import com.interswitch.bookstore.model.CartItem;
//import com.interswitch.bookstore.repository.BookRepository;
//import com.interswitch.bookstore.repository.CartItemRepository;
//import com.interswitch.bookstore.repository.CartRepository;
//import com.interswitch.bookstore.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//
//@Service
//@RequiredArgsConstructor
//public class CartServiceBKUP {
//
//    @Autowired
//    private  CartRepository cartRepository;
//    @Autowired
//    private  BookRepository bookRepository;
//    @Autowired
//    private  CartItemRepository cartItemRepository;
//    @Autowired
//    private  UserRepository userRepository;
//
//    @Transactional
//    public Cart addToCart(Long userId, Long bookId, int quantity) {
//        Book book = bookRepository.findById(bookId)
//                .orElseThrow(() -> new NotFoundException("Book not found with ID: " + bookId));
//
//        AppUser user = userRepository.findById(userId)
//                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));
//
//        Cart cart = cartRepository.findByUser(user).orElseGet(() -> createNewCart(user));
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
//
//    public Cart viewCart(Long cartId) {
//        return cartRepository.findById(cartId)
//                .orElseThrow(() -> new NotFoundException("Cart not found with ID: " + cartId));
//    }
//
//    public Cart findByIdAndUserId(Long cartId, Long userId) {
//        return cartRepository.findByIdAndUserId(cartId, userId)
//                .orElseThrow(() -> new NotFoundException("Cart not found for user ID: " + userId));
//    }
//
//    @Transactional
//    public void removeFromCart(Long cartId, Long bookId) {
//        Cart cart = viewCart(cartId);
//        cart.getCartItems().removeIf(item -> item.getBook().getId().equals(bookId));
//        updateCartTotal(cart);
//        cartRepository.save(cart);
//    }
//
//    @Transactional
//    public void clearCart(Long cartId) {
//        Cart cart = viewCart(cartId);
//        cart.getCartItems().clear();
//        cart.setTotalPrice(0.0);
//        cartRepository.save(cart);
//    }
//
//    private Cart createNewCart(AppUser user) {
//        Cart cart = new Cart();
//        cart.setUser(user);
//        cart.setCartItems(new ArrayList<>());
//        cart.setTotalPrice(0.0);
//        return cartRepository.save(cart);
//    }
//
//    private void updateCartTotal(Cart cart) {
//        double totalPrice = cart.getCartItems()
//                .stream()
//                .mapToDouble(CartItem::getTotalPrice)
//                .sum();
//        cart.setTotalPrice(totalPrice);
//    }
//}
