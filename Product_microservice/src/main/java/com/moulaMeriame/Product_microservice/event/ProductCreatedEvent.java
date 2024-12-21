package com.moulaMeriame.Product_microservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductCreatedEvent {
    private String productId;
    private String name;
    private String description;
    private double price;
    private int stock;
}
