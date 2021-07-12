package com.example.ecommerce_website.service;

import com.example.ecommerce_website.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getProductList();

    Optional<Product> getProduct(Long id);

    Product saveProduct(Product ca);

    void deleteProduct(Long id);

    void updateProduct(Product ca);
}
