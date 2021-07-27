package com.example.ecommerce_website.restcontroller;

import com.example.ecommerce_website.dto.CategoryDTO;
import com.example.ecommerce_website.entity.Category;
import com.example.ecommerce_website.exception.category.CategoryExistedException;
import com.example.ecommerce_website.exception.category.CategoryNotFoundException;
import com.example.ecommerce_website.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body(categoryService.convertToDtoList(categoryService.getCategoryList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(@PathVariable Long id) {
        Optional<Category> ca = categoryService.getCategory(id);
        if (!ca.isPresent()) {
            throw new CategoryNotFoundException(id);
        }
        return ResponseEntity.ok().body(categoryService.convertToDto(ca.get()));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getCategoryByName(@PathVariable String name) {
        Category ca = categoryService.getCategoryByName(name);
        if (ca == null) {
            throw new CategoryNotFoundException(name);
        }
        return ResponseEntity.ok().body(categoryService.convertToDto(ca));
    }

    @PostMapping("/admin")
    public ResponseEntity<?> createCategory(@RequestBody CategoryDTO categoryDTO) throws ParseException {
        if (categoryService.exist(categoryDTO.getName()))
            throw new CategoryExistedException(categoryDTO.getName());
        categoryService.saveCategory(categoryService.convertToEntity(categoryDTO));
        return ResponseEntity.ok().body(categoryDTO);
    }

    @PutMapping("/admin")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryDTO categoryUpdate) throws ParseException {
        if (categoryService.exist(categoryUpdate.getName()))
            throw new CategoryExistedException(categoryUpdate.getName());
        Category category = categoryService.convertToEntity(categoryUpdate);
        categoryService.updateCategory(category);
        return ResponseEntity.ok().body("Update successfully!");
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        Optional<Category> ca = categoryService.getCategory(id);
        if (!ca.isPresent()) {
            throw new CategoryNotFoundException(id);
        }
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().body("Delete successfully!");
    }
}
