package com.example.ecommerce_website.service.impl;

import com.example.ecommerce_website.entity.Category;
import com.example.ecommerce_website.repository.CategoryRepository;
import com.example.ecommerce_website.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository repo;

    public void setCategoryRepo(CategoryRepository repo) { this.repo = repo; }

    public List<Category> getCategoryList() { return repo.findAll(); }

    public Optional<Category> getCategory(Long id) { return repo.findById(id); }

    public Category saveCategory(Category ca) { return repo.save(ca); }

    public void deleteCategory(Long id) { repo.delete(getCategory(id).get()); }

    public void updateCategory(Category ca) { repo.save(ca); }
}