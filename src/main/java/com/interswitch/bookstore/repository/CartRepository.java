package com.interswitch.bookstore.repository;

import com.interswitch.bookstore.model.AppUser;
import com.interswitch.bookstore.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserId(Long userId);

    Optional<Cart> findByIdAndUserId(Long cartId, Long userId);

    Optional<Cart> findByUserAndIsPaidFalse(AppUser user);



}
