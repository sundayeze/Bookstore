package com.interswitch.bookstore.repository;


import com.interswitch.bookstore.model.Cart;
import com.interswitch.bookstore.model.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {
    Optional<Checkout> findByCart(Cart cart);
}
