package com.warehouse.warehouse;

import com.warehouse.warehouse.model.BorrowRecord;
import com.warehouse.warehouse.model.Inventory;
import com.warehouse.warehouse.model.User;
import com.warehouse.warehouse.repository.BorrowRecordRepository;
import com.warehouse.warehouse.repository.InventoryRepository;
import com.warehouse.warehouse.service.BorrowRecordService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
public class BorrowRecordServiceTest {

    @Mock
    private BorrowRecordRepository borrowRecordRepository;

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private BorrowRecordService borrowRecordService;

    @Test
    public void borrowProduct_Success() {
        User user = createUser();
        Inventory inventory = createInventory();
        BorrowRecord borrowRecord = createBorrowRecord(user, inventory, 2);
        Mockito.when(inventoryRepository.findById(1L)).thenReturn(Optional.of(inventory));
        Mockito.when(inventoryRepository.save(Mockito.any(Inventory.class))).thenReturn(inventory);
        Mockito.when(borrowRecordRepository.save(Mockito.any(BorrowRecord.class))).thenReturn(borrowRecord);
        BorrowRecord savedBorrowRecord = borrowRecordService.borrowProduct(user, inventory, 2);
        assertBorrowRecord(savedBorrowRecord, inventory, 2, 8);
    }

    @Test
    public void returnProduct_Success() {
        Inventory inventory = createInventory();
        BorrowRecord borrowRecord = createBorrowRecord(new User(), inventory, 2);
        Mockito.when(borrowRecordRepository.findById(1L)).thenReturn(Optional.of(borrowRecord));
        Mockito.when(borrowRecordRepository.save(Mockito.any(BorrowRecord.class))).thenReturn(borrowRecord);
        Mockito.when(inventoryRepository.save(Mockito.any(Inventory.class))).thenReturn(inventory);
        BorrowRecord returnedBorrowRecord = borrowRecordService.returnProduct(1L);
        assertReturnedBorrowRecord(returnedBorrowRecord, inventory, 12);
    }

    private User createUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        return user;
    }

    private Inventory createInventory() {
        Inventory inventory = new Inventory();
        inventory.setId(1L);
        inventory.setName("Laptop");
        inventory.setQuantity(10);
        inventory.setProductType("Electronics");
        return inventory;
    }

    private BorrowRecord createBorrowRecord(User user, Inventory inventory, int quantity) {
        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.setUser(user);
        borrowRecord.setInventory(inventory);
        borrowRecord.setQuantity(quantity);
        borrowRecord.setBorrowDate(LocalDateTime.now());
        return borrowRecord;
    }

    private void assertBorrowRecord(BorrowRecord borrowRecord, Inventory inventory, int expectedQuantity, int expectedInventoryQuantity) {
        Assertions.assertNotNull(borrowRecord);
        Assertions.assertEquals(expectedQuantity, borrowRecord.getQuantity());
        Assertions.assertEquals(expectedInventoryQuantity, inventory.getQuantity());
    }

    private void assertReturnedBorrowRecord(BorrowRecord borrowRecord, Inventory inventory, int expectedInventoryQuantity) {
        Assertions.assertNotNull(borrowRecord);
        Assertions.assertNotNull(borrowRecord.getReturnDate());
        Assertions.assertEquals(expectedInventoryQuantity, inventory.getQuantity());
    }
}
