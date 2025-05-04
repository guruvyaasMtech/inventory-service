package com.libManager.inventory_service.repository;

import com.libManager.inventory_service.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
 
    // Add more custom query methods as needed
}