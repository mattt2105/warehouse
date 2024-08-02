package com.warehouse.warehouse.controller;


import com.warehouse.warehouse.model.Inventory;
import com.warehouse.warehouse.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    InventoryService inventoryService;


    @PostMapping
    public ResponseEntity<Inventory> addProduct(@RequestBody Inventory inventory) {
        Inventory savedInventory = inventoryService.addProduct(inventory);
        return new ResponseEntity<>(savedInventory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/increase")
    public ResponseEntity<Inventory> increaseProductQuantity(@PathVariable Long id, @RequestParam int quantity) {
        Inventory updatedInventory = inventoryService.increaseProductQuantity(id, quantity);
        return new ResponseEntity<>(updatedInventory, HttpStatus.OK);
    }
}
