package com.example.ecommerce_website.repository;

import com.example.ecommerce_website.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    public Category findCategoryByName(String name);
}
