package com.libManager.inventory_service.model;

import jakarta.persistence.*;

@Entity
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String bookIsbn; // Foreign key referencing the Book Catalog Service

    @Column(nullable = false)
    private String itemCode; // Unique code for each physical copy

    @Enumerated(EnumType.STRING)
    private ItemStatus status; // e.g., AVAILABLE, CHECKED_OUT, LOST

    // Default constructor
    public InventoryItem() {
    }

    public InventoryItem(String bookIsbn, String itemCode, ItemStatus status) {
        this.bookIsbn = bookIsbn;
        this.itemCode = itemCode;
        this.status = status;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }
}