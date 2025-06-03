package com.techm.ecommerce.controller;

import com.techm.ecommerce.domain.USER_ROLE;
import com.techm.ecommerce.model.Product;
import com.techm.ecommerce.model.User;
import com.techm.ecommerce.service.ProductService;
import com.techm.ecommerce.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ProductServiceImpl productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product,
                                              @RequestHeader("Authorization") String jwt) throws Exception {
        User admin = productService.findUserByJwt(jwt);

        if(admin == null || admin.getRole() != USER_ROLE.ROLE_ADMIN) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(productService.addProduct(product), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,
                                                 @RequestBody Product product,
                                                 @RequestHeader("Authorization") String jwt) throws Exception {
        User admin = productService.findUserByJwt(jwt);

        if(admin == null || admin.getRole() != USER_ROLE.ROLE_ADMIN) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id,
                                                @RequestHeader("Authorization") String jwt) throws Exception {
        User admin = productService.findUserByJwt(jwt);

        if(admin == null || admin.getRole() != USER_ROLE.ROLE_ADMIN) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }
}
