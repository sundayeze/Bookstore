package com.interswitch.bookstore;

import com.interswitch.bookstore.exceptions.NotFoundException;
import com.interswitch.bookstore.model.PurchaseHistory;
import com.interswitch.bookstore.repository.PurchaseHistoryRepository;
import com.interswitch.bookstore.service.PurchaseHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurchaseHistoryServiceTest {

    @Mock
    private PurchaseHistoryRepository purchaseHistoryRepository;

    @InjectMocks
    private PurchaseHistoryService purchaseHistoryService;

    private List<PurchaseHistory> mockPurchaseHistories;

    @BeforeEach
    void setUp() {
        PurchaseHistory purchaseHistory = new PurchaseHistory();
        purchaseHistory.setId(1L);

        PurchaseHistory history2 = new PurchaseHistory();
        history2.setId(2L);

        mockPurchaseHistories = new ArrayList<>();
        mockPurchaseHistories.add(purchaseHistory);
        mockPurchaseHistories.add(history2);
    }

    @Test
    void testGetPurchaseHistory_WhenHistoryExists() {
        when(purchaseHistoryRepository.findByUserId(1L)).thenReturn(mockPurchaseHistories);

        List<PurchaseHistory> result = purchaseHistoryService.getPurchaseHistory(1L);

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(purchaseHistoryRepository, times(1)).findByUserId(1L);
    }

    @Test
    void testGetPurchaseHistory_WhenNoHistoryExists() {
        when(purchaseHistoryRepository.findByUserId(2L)).thenReturn(new ArrayList<>());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> purchaseHistoryService.getPurchaseHistory(2L));

        assertEquals("No purchase history found for user ID: 2", exception.getMessage());

        verify(purchaseHistoryRepository, times(1)).findByUserId(2L);
    }
}
