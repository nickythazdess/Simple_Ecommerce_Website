package com.example.ecommerce_website.repository;

import com.example.ecommerce_website.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CategoryRepositoryTest {
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void findCategoryByNameTest() {
        Optional<Category> category = categoryRepository.findById(1L);
        if (category.isPresent()) {
            Category categoryTest = categoryRepository.findCategoryByName(category.get().getName());
            assertNotNull(categoryTest);
            assertEquals(category.get().getId(), categoryTest.getId());
            assertEquals(category.get().getName(), categoryTest.getName());
        }
    }
}