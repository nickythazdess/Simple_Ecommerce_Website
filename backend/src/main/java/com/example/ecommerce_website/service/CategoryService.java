package com.example.ecommerce_website.service;

import com.example.ecommerce_website.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> getCategoryList();

    Optional<Category> getCategory(Long id);

    Category getCategoryByName(String name);

    Category saveCategory(Category ca);

    void deleteCategory(Long id);

    void updateCategory(Category ca);
}
