package com.moulaMeriame.User_microservice.controllers;

import com.moulaMeriame.User_microservice.entities.User;
import com.moulaMeriame.User_microservice.query.FindUserByIdQuery;
import com.moulaMeriame.User_microservice.query.FindUserByUsernameQuery;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users/queries")
public class UserQueryController {

    @Autowired
    private QueryGateway queryGateway;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{id}")
    @CircuitBreaker(name = "userService", fallbackMethod = "fallback")
    @Retry(name = "userService")
    @RateLimiter(name = "userService")
    public User getUserById(@PathVariable String id) {
        return queryGateway.query(new FindUserByIdQuery(id), User.class).join();
    }

    @GetMapping("/by-username/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return queryGateway.query(new FindUserByUsernameQuery(username), User.class).join();
    }

    @GetMapping("/products-by-user/{userId}")
    public Object getProductsByUser(@PathVariable String userId) {
        ResponseEntity<Object> response = restTemplate.exchange(
                "http://PRODUCT-SERVER/products/query",
                HttpMethod.GET,
                null,
                Object.class
        );
        return response.getBody();
    }
    public void fallback(String id, Throwable throwable) {
        System.out.println("User microservice is down :(");; // Return a default UserEntity or handle gracefully
    }
}
