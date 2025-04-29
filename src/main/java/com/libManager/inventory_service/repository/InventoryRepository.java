package com.libManager.inventory_service.repository;

import com.libManager.inventory_service.model.InventoryItem;
import com.libManager.inventory_service.model.ItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InventoryRepository extends JpaRepository<InventoryItem, Long> {
    List<InventoryItem> findByBookIsbn(String bookIsbn);
    InventoryItem findByItemCode(String itemCode);
    List<InventoryItem> findByBookIsbnAndStatus(String bookIsbn, ItemStatus status);
    // Add more custom query methods as needed
}