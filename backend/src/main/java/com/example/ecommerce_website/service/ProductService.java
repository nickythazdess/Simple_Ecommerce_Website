package com.example.ecommerce_website.service;

import com.example.ecommerce_website.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getProductList();

    Optional<Product> getProduct(Long id);

    public Product getProductByName(String name);

    public Product getProductByDev(String dev);

    Product saveProduct(Product product);

    void deleteProduct(Long id);

    void updateProduct(Product product);
}
