package com.warehouse.warehouse;

import com.warehouse.warehouse.model.Inventory;
import com.warehouse.warehouse.repository.InventoryRepository;
import com.warehouse.warehouse.service.InventoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryService inventoryService;

    @Test
    public void addProduct_Success() {
        Inventory inventory = createInventory();
        Mockito.when(inventoryRepository.save(Mockito.any(Inventory.class))).thenReturn(inventory);
        Inventory savedInventory = inventoryService.addProduct(inventory);
        assertInventory(savedInventory);
    }

    @Test
    public void increaseProductQuantity_Success() {
        Inventory inventory = createInventory();
        Mockito.when(inventoryRepository.findById(1L)).thenReturn(Optional.of(inventory));
        Mockito.when(inventoryRepository.save(Mockito.any(Inventory.class))).thenReturn(inventory);
        Inventory updatedInventory = inventoryService.increaseProductQuantity(1L, 5);
        Assertions.assertNotNull(updatedInventory);
        Assertions.assertEquals(15, updatedInventory.getQuantity());
    }

    private Inventory createInventory() {
        Inventory inventory = new Inventory();
        inventory.setId(1L);
        inventory.setName("Laptop");
        inventory.setQuantity(10);
        inventory.setProductType("Electronics");
        return inventory;
    }

    private void assertInventory(Inventory inventory) {
        Assertions.assertNotNull(inventory);
        Assertions.assertEquals("Laptop", inventory.getName());
    }
}
