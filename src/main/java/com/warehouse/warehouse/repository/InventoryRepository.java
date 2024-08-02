package com.warehouse.warehouse.repository;

import com.warehouse.warehouse.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
