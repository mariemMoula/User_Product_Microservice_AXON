package com.moulaMeriame.Product_microservice.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CreateProductCommand {
    @TargetAggregateIdentifier
    private String productId;
    private String name;
    private String description;
    private double price;
    private int stock;
}
