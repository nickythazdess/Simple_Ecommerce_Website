package com.example.ecommerce_website.dto;

import com.example.ecommerce_website.entity.Category;
import com.example.ecommerce_website.entity.Product;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductDTOTest {
    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void whenConvertProductEntityToPostDTOTest() {
        Product product = new Product();
        Category category = new Category();
        category.setId(10L);
        category.setName("Spaceee");
        product.setId(10L);
        product.setName("Mario");
        product.setCategory(category);
        product.setPrice(49.99f);
        //product.setDescription("hello");

        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        productDTO.setCategory_name(product.getCategory().getName());

        assertEquals(product.getId(), productDTO.getId());
        assertEquals(product.getName(), productDTO.getName());
        assertEquals(product.getCategory().getName(), productDTO.getCategory_name());
        assertEquals(product.getPrice(), productDTO.getPrice(), 0);
        //assertEquals(product.getDescription(), productDTO.getDescription());
    }

    @Test
    public void whenConvertPostDtoToPostEntityTest() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(10L);
        productDTO.setName("abc");
        productDTO.setCategory_name("xyz");
        productDTO.setPrice(69.99f);
        //productDTO.setDescription("hello");

        Product product = modelMapper.map(productDTO, Product.class);
        Category category = new Category();
        category.setId(10L);
        category.setName(productDTO.getCategory_name());
        product.setCategory(category);

        assertEquals(product.getId(), productDTO.getId());
        assertEquals(product.getName(), productDTO.getName());
        assertEquals(product.getCategory().getName(), productDTO.getCategory_name());
        assertEquals(product.getPrice(), productDTO.getPrice(), 0);
        //assertEquals(product.getDescription(), productDTO.getDescription());
    }
}