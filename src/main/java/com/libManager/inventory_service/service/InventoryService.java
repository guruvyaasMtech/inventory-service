package com.libManager.inventory_service.service;

import com.libManager.inventory_service.model.InventoryItem;
import com.libManager.inventory_service.model.ItemStatus;
import com.libManager.inventory_service.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<InventoryItem> getAllInventoryItems() {
        return inventoryRepository.findAll();
    }

    public Optional<InventoryItem> getInventoryItemById(Long id) {
        return inventoryRepository.findById(id);
    }

    public List<InventoryItem> getInventoryItemsByBookIsbn(String bookIsbn) {
        return inventoryRepository.findByBookIsbn(bookIsbn);
    }

    public InventoryItem getInventoryItemByItemCode(String itemCode) {
        return inventoryRepository.findByItemCode(itemCode);
    }

    public List<InventoryItem> getAvailableInventoryItemsByBookIsbn(String bookIsbn) {
        return inventoryRepository.findByBookIsbnAndStatus(bookIsbn, ItemStatus.AVAILABLE);
    }

    public void addInventoryItem(InventoryItem inventoryItem) {
        // Add business logic here, e.g., checking for duplicate item codes
        inventoryRepository.save(inventoryItem);
    }

    public void updateInventoryItem(Long id, InventoryItem updatedItem) {
        Optional<InventoryItem> existingItem = inventoryRepository.findById(id);
        existingItem.ifPresent(item -> {
            updatedItem.setId(item.getId());
            inventoryRepository.save(updatedItem);
        });
        // Handle the case where the item doesn't exist
    }

    public void updateItemStatus(String itemCode, ItemStatus newStatus) {
        InventoryItem item = inventoryRepository.findByItemCode(itemCode);
        if (item != null) {
            item.setStatus(newStatus);
            inventoryRepository.save(item);
        }
        // Handle the case where the item doesn't exist
    }

    public void deleteInventoryItem(Long id) {
        inventoryRepository.deleteById(id);
    }
}