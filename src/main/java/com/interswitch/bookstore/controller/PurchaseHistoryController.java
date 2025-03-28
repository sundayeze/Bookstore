package com.interswitch.bookstore.controller;

import com.interswitch.bookstore.model.PurchaseHistory;
import com.interswitch.bookstore.service.PurchaseHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase-history")
@RequiredArgsConstructor
public class PurchaseHistoryController {

    @Autowired
    private PurchaseHistoryService purchaseHistoryService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<PurchaseHistory>> getPurchaseHistory(@PathVariable Long userId) {
        List<PurchaseHistory> history = purchaseHistoryService.getPurchaseHistory(userId);
        return ResponseEntity.ok(history);
    }
}
