package com.example.ecommerce_website.restcontroller;

import com.example.ecommerce_website.entity.Category;
import com.example.ecommerce_website.exception.CategoryNotFoundException;
import com.example.ecommerce_website.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /*@GetMapping()
    public List<Category> findAll() {
        return categoryService.getCategoryList();
    }*/

    @GetMapping()
    public ResponseEntity<List<Category>> findAll() {
        return new ResponseEntity<>(categoryService.getCategoryList(), HttpStatus.OK);
    }

    /*@GetMapping("/{id}")
    public Category findCategory(@PathVariable Long id) {
        Category ca = categoryService.getCategory(id);
        if (ca != null) return ca;
        else throw new CategoryNotFoundException(id);
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<Category> findCategory(@PathVariable Long id) {
        Optional<Category> ca = categoryService.getCategory(id);
        if (!ca.isPresent()) {
            throw new CategoryNotFoundException(id);
        }
        return new ResponseEntity<>(ca.get(), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Category> findCategoryByName(@PathVariable String name) {
        Category ca = categoryService.getCategoryByName(name);
        if (ca == null) {
            throw new CategoryNotFoundException(name);
        }
        return new ResponseEntity<>(ca, HttpStatus.OK);
    }

    @PostMapping()
    public Category saveCategory(@Valid @RequestBody Category ca) { return categoryService.saveCategory(ca); }

    @PutMapping()
    public HashMap<String, String> updateCategory(@Valid @RequestBody Category categoryUpdate) {
        categoryService.updateCategory(categoryUpdate);
        HashMap<String, String> map = new HashMap<>();
        map.put("message", "Update successfully!");
        return map;
    }

    @DeleteMapping("/{id}")
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
