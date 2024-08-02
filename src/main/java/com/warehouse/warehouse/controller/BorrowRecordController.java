package com.warehouse.warehouse.controller;


import com.warehouse.warehouse.model.BorrowRecord;
import com.warehouse.warehouse.model.Inventory;
import com.warehouse.warehouse.model.User;
import com.warehouse.warehouse.repository.InventoryRepository;
import com.warehouse.warehouse.repository.UserRepository;
import com.warehouse.warehouse.service.BorrowRecordService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrow")
public class BorrowRecordController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    BorrowRecordService borrowRecordService;

    @PostMapping
    public ResponseEntity<BorrowRecord> borrowProduct(@RequestParam Long userId, @RequestParam Long inventoryId, @RequestParam int quantity) {
        User user = getUserById(userId);
        Inventory inventory = getInventoryById(inventoryId);
        BorrowRecord borrowRecord = borrowRecordService.borrowProduct(user, inventory, quantity);
        return new ResponseEntity<>(borrowRecord, HttpStatus.CREATED);
    }


    @PutMapping("/{id}/return")
    public ResponseEntity<BorrowRecord> returnProduct(@PathVariable Long id) {
        BorrowRecord borrowRecord = borrowRecordService.returnProduct(id);
        return new ResponseEntity<>(borrowRecord, HttpStatus.OK);
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    }

    private Inventory getInventoryById(Long inventoryId) {
        return inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new EntityNotFoundException("Inventory not found with id: " + inventoryId));
    }
}
