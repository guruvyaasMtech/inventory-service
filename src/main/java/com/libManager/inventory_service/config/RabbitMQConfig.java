package com.libManager.inventory_service.config;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.BindingBuilder.GenericArgumentsConfigurer;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    private final String BOOK_CREATED_ROUTING_KEY = "book.created";

    @Bean
    public Queue bookCreatedQueue() {
        return new Queue("book.created.queue", true);
    }

    @Bean
    public Binding bookCreatedBinding(Queue bookCreatedQueue, TopicExchange bookCreatedExchange) {
        return BindingBuilder
                .bind(bookCreatedQueue)
                .to(bookCreatedExchange)
                .with(BOOK_CREATED_ROUTING_KEY);
    }

    @Bean
    public TopicExchange bookCreatedExchange() {
        return new TopicExchange("book.exchange");
    }
}



