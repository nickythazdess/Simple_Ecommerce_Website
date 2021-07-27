package com.example.ecommerce_website.repository;

import com.example.ecommerce_website.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findProductByName(String name);
    Product findProductByDev(String dev);
    Boolean existsByName(String name);
    List<Product> findAllByCategory_name(String category_name);

    List<Product> findByNameContainingIgnoreCase(String name);
}
