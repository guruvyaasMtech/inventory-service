package com.libManager.inventory_service.controller;

import com.libManager.inventory_service.model.InventoryItem;
import com.libManager.inventory_service.model.ItemStatus;
import com.libManager.inventory_service.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public ResponseEntity<List<InventoryItem>> getAllInventoryItems() {
        List<InventoryItem> items = inventoryService.getAllInventoryItems();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryItem> getInventoryItemById(@PathVariable Long id) {
        Optional<InventoryItem> item = inventoryService.getInventoryItemById(id);
        return item.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                   .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/isbn/{bookIsbn}")
    public ResponseEntity<List<InventoryItem>> getInventoryItemsByBookIsbn(@PathVariable String bookIsbn) {
        List<InventoryItem> items = inventoryService.getInventoryItemsByBookIsbn(bookIsbn);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/code/{itemCode}")
    public ResponseEntity<InventoryItem> getInventoryItemByItemCode(@PathVariable String itemCode) {
        InventoryItem item = inventoryService.getInventoryItemByItemCode(itemCode);
        return item != null ? new ResponseEntity<>(item, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/isbn/{bookIsbn}/available")
    public ResponseEntity<List<InventoryItem>> getAvailableInventoryItemsByBookIsbn(@PathVariable String bookIsbn) {
        List<InventoryItem> availableItems = inventoryService.getAvailableInventoryItemsByBookIsbn(bookIsbn);
        return new ResponseEntity<>(availableItems, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> addInventoryItem(@RequestBody InventoryItem inventoryItem) {
        inventoryService.addInventoryItem(inventoryItem);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateInventoryItem(@PathVariable Long id, @RequestBody InventoryItem updatedItem) {
        inventoryService.updateInventoryItem(id, updatedItem);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/code/{itemCode}/status")
    public ResponseEntity<Void> updateItemStatus(@PathVariable String itemCode, @RequestParam ItemStatus status) {
        inventoryService.updateItemStatus(itemCode, status);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventoryItem(@PathVariable Long id) {
        inventoryService.deleteInventoryItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}