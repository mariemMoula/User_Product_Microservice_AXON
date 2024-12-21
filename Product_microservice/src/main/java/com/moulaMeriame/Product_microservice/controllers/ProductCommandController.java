package com.moulaMeriame.Product_microservice.controllers;

import com.moulaMeriame.Product_microservice.command.CreateProductCommand;
import com.moulaMeriame.Product_microservice.command.DeleteProductCommand;
import com.moulaMeriame.Product_microservice.command.UpdateProductCommand;
import com.moulaMeriame.Product_microservice.projection.ProductProjection;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/products/command")
public class ProductCommandController {

    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private ProductProjection productProjection;

    @PostMapping
    @CircuitBreaker(name = "productService", fallbackMethod = "fallback")
    @Retry(name = "productService")
    @RateLimiter(name = "productService")
    public String createProduct(@RequestBody CreateProductCommand command) {
        return commandGateway.sendAndWait(CreateProductCommand.builder()
                .productId(UUID.randomUUID().toString())
                .name(command.getName())
                .description(command.getDescription())
                .price(command.getPrice())
                .stock(command.getStock())
                .build());
    }

    @PutMapping("/{id}")
    @CircuitBreaker(name = "productService", fallbackMethod = "fallback")
    @Retry(name = "productService")
    @RateLimiter(name = "productService")
    public String updateProduct(@PathVariable String id, @RequestBody UpdateProductCommand command) {
        command.setProductId(id);
        return commandGateway.sendAndWait(command);
    }

    @DeleteMapping("/{id}")
    @CircuitBreaker(name = "productService", fallbackMethod = "fallback")
    @Retry(name = "productService")
    @RateLimiter(name = "productService")
    public String deleteProduct(@PathVariable String id) {
        return commandGateway.sendAndWait(new DeleteProductCommand(id));
    }

    // Fallback method for Circuit Breaker
    public String fallback(Throwable throwable) {
        return "Service is currently unavailable. Please try again later.";
    }
}