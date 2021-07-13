package com.example.ecommerce_website.repository;

import com.example.ecommerce_website.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    public Product findProductByName(String name);
    public Product findProductByDev(String dev);
    //public Product findProductByCategory_id(Category category_id);
}
