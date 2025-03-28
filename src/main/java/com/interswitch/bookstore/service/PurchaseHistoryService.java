package com.interswitch.bookstore.service;

import com.interswitch.bookstore.exceptions.NotFoundException;
import com.interswitch.bookstore.model.PurchaseHistory;
import com.interswitch.bookstore.repository.PurchaseHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseHistoryService {

    @Autowired
    private PurchaseHistoryRepository purchaseHistoryRepository;

    public List<PurchaseHistory> getPurchaseHistory(Long userId) {
        List<PurchaseHistory> history = purchaseHistoryRepository.findByUserId(userId);
        if (history.isEmpty()) {
            throw new NotFoundException("No purchase history found for user ID: " + userId);
        }
        return history;
    }
}
