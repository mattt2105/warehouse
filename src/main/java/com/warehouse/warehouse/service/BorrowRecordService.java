package com.warehouse.warehouse.service;

import com.warehouse.warehouse.model.BorrowRecord;
import com.warehouse.warehouse.model.Inventory;
import com.warehouse.warehouse.model.User;
import com.warehouse.warehouse.repository.BorrowRecordRepository;
import com.warehouse.warehouse.repository.InventoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BorrowRecordService {

    @Autowired
    BorrowRecordRepository borrowRecordRepository;

    @Autowired
    InventoryRepository inventoryRepository;

    public BorrowRecord borrowProduct(User user, Inventory inventory, int quantity) {
        validateBorrowing(inventory, quantity);
        updateInventoryQuantity(inventory, -quantity);
        return createBorrowRecord(user, inventory, quantity);
    }

    public BorrowRecord returnProduct(Long borrowRecordId) {
        BorrowRecord borrowRecord = getBorrowRecordById(borrowRecordId);
        Inventory inventory = borrowRecord.getInventory();
        updateInventoryQuantity(inventory, borrowRecord.getQuantity());
        borrowRecord.setReturnDate(LocalDateTime.now());
        return borrowRecordRepository.save(borrowRecord);
    }

    private void validateBorrowing(Inventory inventory, int quantity) {
        if (inventory.getQuantity() < quantity) {
            throw new IllegalArgumentException("Not enough inventory");
        }
    }

    private void updateInventoryQuantity(Inventory inventory, int quantityChange) {
        inventory.setQuantity(inventory.getQuantity() + quantityChange);
        inventoryRepository.save(inventory);
    }

    private BorrowRecord createBorrowRecord(User user, Inventory inventory, int quantity) {
        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.setUser(user);
        borrowRecord.setInventory(inventory);
        borrowRecord.setQuantity(quantity);
        borrowRecord.setBorrowDate(LocalDateTime.now());
        return borrowRecordRepository.save(borrowRecord);
    }

    private BorrowRecord getBorrowRecordById(Long borrowRecordId) {
        return borrowRecordRepository.findById(borrowRecordId)
                .orElseThrow(() -> new EntityNotFoundException("Borrow record not found with id: " + borrowRecordId));
    }
}
