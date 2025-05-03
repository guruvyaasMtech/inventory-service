package com.libManager.inventory_service.consumer;


import com.libManager.inventory_service.model.Inventory;
import com.libManager.inventory_service.repository.InventoryRepository;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class InventoryEventListener {
    @Autowired
    private InventoryRepository inventoryRepository;
    private final MessageConverter messageConverter = new SimpleMessageConverter();

    @RabbitListener(queues = "book.created.queue")
    @Transactional
    public void handleBookCreatedEvent(org.springframework.amqp.core.Message message) {
        try {
            Long event = (Long) messageConverter.fromMessage(message);
            Long bookId = event;
            if (!inventoryRepository.existsById(bookId)) {
                Inventory inventory = new Inventory();
                inventory.setBookId(bookId);
                inventory.setQuantity(100);
                inventoryRepository.save(inventory);
                System.out.println("Inventory created for book: " + event);
            } else {
                System.out.println("Inventory record already exists for book ID: " + bookId + ".  No action taken.");
            }
        } catch (Exception e) {
            System.err.println("Error processing BookCreatedEvent: " + e.getMessage());
        }
    }
}