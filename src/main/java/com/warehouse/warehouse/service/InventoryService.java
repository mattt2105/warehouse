package com.warehouse.warehouse.service;

import com.warehouse.warehouse.model.Inventory;
import com.warehouse.warehouse.repository.InventoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;

    public Inventory addProduct(Inventory inventory) {
        validateInventory(inventory);
        return inventoryRepository.save(inventory);
    }

    public Inventory increaseProductQuantity(Long id, int quantity) {
        Inventory inventory = getInventoryById(id);
        inventory.setQuantity(inventory.getQuantity() + quantity);
        return inventoryRepository.save(inventory);
    }

    private Inventory getInventoryById(Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inventory not found with id: " + id));
    }

    private void validateInventory(Inventory inventory) {
        if (inventory.getName() == null || inventory.getName().isEmpty()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (inventory.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        if (inventory.getProductType() == null || inventory.getProductType().isEmpty()) {
            throw new IllegalArgumentException("Product type is required");
        }
    }
}
