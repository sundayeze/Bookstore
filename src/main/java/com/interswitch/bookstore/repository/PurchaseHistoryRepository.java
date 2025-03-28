package com.interswitch.bookstore.repository;

import com.interswitch.bookstore.model.AppUser;
import com.interswitch.bookstore.model.Book;
import com.interswitch.bookstore.model.PurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long> {

    List<PurchaseHistory> findByUserId(Long userId);

    Optional<PurchaseHistory> findByUser(AppUser user);

}
