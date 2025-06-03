package com.techm.ecommerce.service.impl;

import com.techm.ecommerce.config.JwtProvider;
import com.techm.ecommerce.model.Product;
import com.techm.ecommerce.model.User;
import com.techm.ecommerce.repository.ProductRepository;
import com.techm.ecommerce.repository.UserRepository;
import com.techm.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;


    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Product oldProduct = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        if(product.getName() != null)
            oldProduct.setName(product.getName());
        if(product.getDescription() != null)
            oldProduct.setDescription(product.getDescription());
        if(product.getPrice() != null)
            oldProduct.setPrice(product.getPrice());
        if(product.getCategory() != null)
            oldProduct.setCategory(product.getCategory());
        if(product.getStockQuantity() != null)
            oldProduct.setStockQuantity(product.getStockQuantity());

        return productRepository.save(oldProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> searchByName(String name) {
        return productRepository.findAllByName(name);
    }

    @Override
    public List<Product> filterByCategoryAndPrice(String category, Double minPrice, Double maxPrice) {
        if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
            throw new IllegalArgumentException("minPrice must be <= maxPrice");
        }
        return productRepository.filterByCategoryAndMinPriceAndMaxPrice(category, minPrice, maxPrice);
    }


    public User findUserByJwt(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);

        User user = userRepository.findUserByEmail(email);

        if (user == null) {
            throw new Exception("user not exist with email " + email);
        }
        return user;
    }

}
