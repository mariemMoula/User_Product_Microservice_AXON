package com.moulaMeriame.Product_microservice.controllers;

import com.moulaMeriame.Product_microservice.entities.Product;
import com.moulaMeriame.Product_microservice.query.FindAllProductsQuery;
import com.moulaMeriame.Product_microservice.query.FindProductByIdQuery;
import com.moulaMeriame.Product_microservice.query.FindProductsByUserQuery;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products/query")
public class ProductQueryController {

    private final QueryGateway queryGateway;
    private final RestTemplate restTemplate;

    @GetMapping("/{id}")
    @CircuitBreaker(name = "productService", fallbackMethod = "fallback")
    @Retry(name = "productService")
    @RateLimiter(name = "productService")
    public Product getProductById(@PathVariable String id) {
        return queryGateway.query(new FindProductByIdQuery(id), Product.class).join();
    }

    @GetMapping
    @CircuitBreaker(name = "productService", fallbackMethod = "fallback")
    @Retry(name = "productService")
    @RateLimiter(name = "productService")
    public List<Product> getAllProducts() {
        return queryGateway.query(new FindAllProductsQuery(), List.class).join();
    }

    @GetMapping("/user/{userId}")
    @CircuitBreaker(name = "productService", fallbackMethod = "fallback")
    @Retry(name = "productService")
    @RateLimiter(name = "productService")
    public List<Product> getProductsByUser(@PathVariable String userId) {
        return queryGateway.query(new FindProductsByUserQuery(userId), List.class).join();
    }

    @GetMapping("/users")
    @CircuitBreaker(name = "productService", fallbackMethod = "fallback")
    @Retry(name = "myRetry", fallbackMethod = "fallback")
    @RateLimiter(name = "myRateLimiter", fallbackMethod = "fallback")
    public Object getAllUserss() {
        ResponseEntity<Object> response = restTemplate.exchange(
                "http://USER-SERVER/users/queries",
                HttpMethod.GET,
                null,
                Object.class
        );
        return response.getBody();
    }

    // Fallback method for Circuit Breaker
    public String fallback(Throwable throwable) {
        return "Product MicroService is currently unavailable. Please try again later.";
    }
}
