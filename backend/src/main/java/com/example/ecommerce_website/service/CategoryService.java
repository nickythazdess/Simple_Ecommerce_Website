package com.example.ecommerce_website.service;

import com.example.ecommerce_website.dto.CategoryDTO;
import com.example.ecommerce_website.entity.Category;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> getCategoryList();

    Optional<Category> getCategory(Long id);

    Category getCategoryByName(String name);

    Category saveCategory(Category ca);

    void deleteCategory(Long id);

    Category updateCategory(Category ca);

    Boolean exist(String name);

    CategoryDTO convertToDto(Category cate);

    List<CategoryDTO> convertToDtoList(List<Category> cateList);

    Category convertToEntity(CategoryDTO categoryDTO) throws ParseException;
}
