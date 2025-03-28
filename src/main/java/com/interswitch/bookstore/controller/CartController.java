package com.interswitch.bookstore.controller;

import com.interswitch.bookstore.exceptions.NotFoundException;
import com.interswitch.bookstore.model.Cart;
import com.interswitch.bookstore.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestParam Long userId, @RequestParam Long bookId, @RequestParam int quantity) {
        try {
            Cart cart = cartService.addToCart(userId, bookId, quantity);
            return ResponseEntity.ok(cart);
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<?> viewCart(@PathVariable Long cartId) {
        try {
            Cart cart = cartService.viewCart(cartId);
            return ResponseEntity.ok(cart);
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}
