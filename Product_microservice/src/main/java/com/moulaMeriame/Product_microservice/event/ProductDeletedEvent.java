package com.moulaMeriame.Product_microservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDeletedEvent {
    private String productId;
}
