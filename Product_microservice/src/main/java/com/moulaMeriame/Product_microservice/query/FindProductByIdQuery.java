package com.moulaMeriame.Product_microservice.query;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindProductByIdQuery {
    private String productId;
}
