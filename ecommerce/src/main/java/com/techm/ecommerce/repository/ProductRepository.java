package com.techm.ecommerce.repository;

import com.techm.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByName(String name);

    @Query("SELECT p FROM Product p " +
            "WHERE (:category IS NULL OR p.category = :category)" +
            " AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice)")
    List<Product> filterByCategoryAndMinPriceAndMaxPrice(@Param("category") String category,
                                                         @Param("minPrice") Double minPrice,
                                                         @Param("maxPrice") Double maxPrice);
}
