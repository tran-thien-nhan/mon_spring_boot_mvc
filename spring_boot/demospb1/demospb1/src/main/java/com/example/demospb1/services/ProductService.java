package com.example.demospb1.services;

import com.example.demospb1.models.Product;
import com.example.demospb1.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Marking this class as a service, so Spring will manage it and inject it into the controller
public class ProductService {

    private final ProductRepository productRepository;

    // Constructor-based dependency injection is generally preferred
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Method to get all products
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    // Method to save a product
    public void save(Product product) {
        productRepository.save(product);
    }
}
