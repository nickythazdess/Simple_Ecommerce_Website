package com.example.ecommerce_website.service.impl;

import com.example.ecommerce_website.dto.CategoryDTO;
import com.example.ecommerce_website.entity.Category;
import com.example.ecommerce_website.repository.CategoryRepository;
import com.example.ecommerce_website.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository repo;

    @Autowired
    private ModelMapper modelMapper;

    public void setCategoryRepo(CategoryRepository repo) { this.repo = repo; }

    public List<Category> getCategoryList() { return repo.findAll(); }

    public Optional<Category> getCategory(Long id) { return repo.findById(id); }

    public Category getCategoryByName(String name) { return repo.findCategoryByName(name); }

    public Category saveCategory(Category ca) { return repo.save(ca); }

    public void deleteCategory(Long id) { repo.delete(getCategory(id).get()); }

    public Category updateCategory(Category ca) { return repo.save(ca); }

    public Boolean exist(String name) { return repo.existsByName(name); }

    public CategoryDTO convertToDto(Category cate){
        CategoryDTO categoryDTO = modelMapper.map(cate, CategoryDTO.class);
        return categoryDTO;
    }

    public List<CategoryDTO> convertToDtoList(List<Category> cateList){
        List<CategoryDTO> dtoList = new ArrayList<>();
        for (Category cate : cateList) {
            dtoList.add(convertToDto(cate));
        }
        return dtoList;
    }

    public Category convertToEntity(CategoryDTO categoryDTO) throws ParseException {
        Category cate = modelMapper.map(categoryDTO, Category.class);
        return cate;
    }
}