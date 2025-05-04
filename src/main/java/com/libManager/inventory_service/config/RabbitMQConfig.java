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
    @RabbitListener(queues = "book.updated.queue")
    @Transactional
    public void handleBookUpdatedEvent(org.springframework.amqp.core.Message message) {
        try {
            String messageBody = new String(message.getBody(), "UTF-8");
            String[] parts = messageBody.split("/");
            if (parts.length>0) {
                Long bookId = Long.parseLong(parts[0]);
                String action = parts[1];
                int change=1;
                if(action.equals("checkout")) {
                	change=-1;
                }

                Inventory inventory = inventoryRepository.findById(bookId).orElse(null);
                if (inventory != null) {
                    int newQuantity = inventory.getQuantity() + change;
                    inventory.setQuantity(newQuantity);
                    inventoryRepository.save(inventory);
                    System.out.println("Inventory updated for book ID: " + bookId + " New Quantity: " + newQuantity);
                } else {
                    System.out.println("Inventory not found for book ID: " + bookId);
                }
            } else {
                System.err.println("Invalid message format: " + messageBody);
            }

        } catch (Exception e) {
            System.err.println("Error processing BookUpdatedEvent: " + e.getMessage());
        }
    }
}
