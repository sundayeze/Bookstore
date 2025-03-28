package com.interswitch.bookstore.repository;


import com.interswitch.bookstore.model.AppUser;
import com.interswitch.bookstore.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);

//    @Query("SELECT c FROM Cart c WHERE c.user = :user AND c.isPaid = false")
//    Optional<Cart> findUnpaidCartByUser(@Param("user") AppUser user);

}
