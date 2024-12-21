package com.moulaMeriame.Product_microservice.projection;
import com.moulaMeriame.Product_microservice.entities.Product;
import com.moulaMeriame.Product_microservice.event.ProductCreatedEvent;
import com.moulaMeriame.Product_microservice.event.ProductDeletedEvent;
import com.moulaMeriame.Product_microservice.event.ProductUpdatedEvent;
import com.moulaMeriame.Product_microservice.query.FindAllProductsQuery;
import com.moulaMeriame.Product_microservice.query.FindProductByIdQuery;
import com.moulaMeriame.Product_microservice.query.FindProductsByUserQuery;
import com.moulaMeriame.Product_microservice.repositories.ProductRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductProjection {
    @Autowired
    private ProductRepository repository;

    @EventHandler
    public void on(ProductCreatedEvent event) {
        Product product = new Product();
        product.setId(event.getProductId());
        product.setName(event.getName());
        product.setDescription(event.getDescription());
        product.setPrice(event.getPrice());
        product.setStock(event.getStock());
        repository.save(product);
    }

    @EventHandler
    public void on(ProductUpdatedEvent event) {
        Product product = repository.findById(event.getProductId()).orElseThrow();
        product.setName(event.getName());
        product.setDescription(event.getDescription());
        product.setPrice(event.getPrice());
        product.setStock(event.getStock());
        repository.save(product);
    }

    @EventHandler
    public void on(ProductDeletedEvent event) {
        repository.deleteById(event.getProductId());
    }

    @QueryHandler
    public Product handle(FindProductByIdQuery query) {
        return repository.findById(query.getProductId()).orElse(null);
    }

    @QueryHandler
    public List<Product> handle(FindAllProductsQuery query) {
        return repository.findAll();
    }
}
