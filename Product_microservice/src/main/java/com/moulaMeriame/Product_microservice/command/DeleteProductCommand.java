package com.moulaMeriame.Product_microservice.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
@AllArgsConstructor
public class DeleteProductCommand {
    @TargetAggregateIdentifier
    private String productId;
}
