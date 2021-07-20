package com.example.ecommerce_website.restcontroller;

import com.example.ecommerce_website.dto.CategoryDTO;
import com.example.ecommerce_website.entity.Category;
import com.example.ecommerce_website.exception.category.CategoryExistedException;
import com.example.ecommerce_website.exception.category.CategoryNotFoundException;
import com.example.ecommerce_website.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<List<CategoryDTO>> getAll() {
        return new ResponseEntity<>(categoryService.convertToDtoList(categoryService.getCategoryList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long id) {
        Optional<Category> ca = categoryService.getCategory(id);
        if (!ca.isPresent()) {
            throw new CategoryNotFoundException(id);
        }
        return new ResponseEntity<>(categoryService.convertToDto(ca.get()), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable String name) {
        Category ca = categoryService.getCategoryByName(name);
        if (ca == null) {
            throw new CategoryNotFoundException(name);
        }
        return new ResponseEntity<>(categoryService.convertToDto(ca), HttpStatus.OK);
    }

    @PostMapping("/admin")
    public CategoryDTO createCategory(@Valid @RequestBody CategoryDTO categoryDTO) throws ParseException {
        if (categoryService.exist(categoryDTO.getName()))
            throw new CategoryExistedException(categoryDTO.getName());
        categoryService.saveCategory(categoryService.convertToEntity(categoryDTO));
        return categoryDTO;
    }

    @PutMapping("/admin")
    public CategoryDTO updateCategory(@Valid @RequestBody(required = true) CategoryDTO categoryUpdate) throws ParseException {
        Category category = categoryService.convertToEntity(categoryUpdate);
        categoryService.updateCategory(category);
        return categoryUpdate;
    }

    @DeleteMapping("/admin/{id}")
    public HashMap<String, String> deleteCategory(@PathVariable Long id) {
        Optional<Category> ca = categoryService.getCategory(id);
        if (!ca.isPresent()) {
            throw new CategoryNotFoundException(id);
        }
        categoryService.deleteCategory(id);
        HashMap<String, String> map = new HashMap<>();
        map.put("message", "Delete successfully!");
        return map;
    }
}
