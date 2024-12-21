package com.moulaMeriame.Product_microservice.aggregate;

import com.moulaMeriame.Product_microservice.command.CreateProductCommand;
import com.moulaMeriame.Product_microservice.command.DeleteProductCommand;
import com.moulaMeriame.Product_microservice.command.UpdateProductCommand;
import com.moulaMeriame.Product_microservice.event.ProductCreatedEvent;
import com.moulaMeriame.Product_microservice.event.ProductDeletedEvent;
import com.moulaMeriame.Product_microservice.event.ProductUpdatedEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import static org.axonframework.modelling.command.AggregateLifecycle.markDeleted;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Aggregate
public class ProductAggregate {

    @AggregateIdentifier
    private String id;
    private String name;
    private String description;
    private double price;
    private int stock;
    // Create Product
    @CommandHandler
    public ProductAggregate(CreateProductCommand command) {
        // Validation logic here

        apply(new ProductCreatedEvent(
                command.getProductId(),
                command.getName(),
                command.getDescription(),
                command.getPrice(),
                command.getStock()
        ));
    }

    // Update Product
    @CommandHandler
    public void handle(UpdateProductCommand command) {
        apply(new ProductUpdatedEvent(
                command.getProductId(),
                command.getName(),
                command.getDescription(),
                command.getPrice(),
                command.getStock()
        ));
    }

    // Delete Product
    @CommandHandler
    public void handle(DeleteProductCommand command) {
        apply(new ProductDeletedEvent(command.getProductId()));
    }


    @EventSourcingHandler
    public void on(ProductCreatedEvent event) {
        this.id = event.getProductId();
        this.name = event.getName();
        this.description = event.getDescription();
        this.price = event.getPrice();
        this.stock = event.getStock();
    }

    @EventSourcingHandler
    public void on(ProductUpdatedEvent event) {
        this.name = event.getName();
        this.description = event.getDescription();
        this.price = event.getPrice();
        this.stock = event.getStock();
    }

    @EventSourcingHandler
    public void on(ProductDeletedEvent event) {
        markDeleted();
    }

}
