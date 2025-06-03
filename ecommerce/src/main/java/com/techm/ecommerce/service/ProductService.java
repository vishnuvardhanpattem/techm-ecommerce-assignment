package com.techm.ecommerce.service;

import com.techm.ecommerce.model.Product;
import java.util.List;

public interface ProductService {
    Product addProduct(Product product);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
    Product getProductById(Long id);
    List<Product> getAllProducts();
    List<Product> searchByName(String name);
    List<Product> filterByCategoryAndPrice(String category, Double minPrice, Double maxPrice);
}


//To develop a secure REST API for product management.
//
//To implement CRUD operations for products using Spring Data JPA and MySQL.
//
//To provide filtering and search functionality for better user experience.
//
//To secure the API using Spring Security.