package com.example.ecommerce_website.service;

import com.example.ecommerce_website.dto.ProductDTO;
import com.example.ecommerce_website.entity.Category;
import com.example.ecommerce_website.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getProductList();

    Optional<Product> getProduct(Long id);

    public Product getProductByName(String name);

    public Product getProductByDev(String dev);

    List<Product> getProductsByCategory(String category_name);

    Product saveProduct(Product product);

    Product updateProduct(Product product);

    void deleteProduct(Long id);

    Boolean exist(String name);

    ProductDTO convertToDto(Product product);

    List<ProductDTO> convertToDtoList(List<Product> productList);

    Product convertToEntity(ProductDTO productDTO) throws ParseException;
}
