package com.example.ecommerce_website.service;

import com.example.ecommerce_website.displayDTO.ProductDisplay;
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

    Product getProductByName(String name);

    List<Product> searchProductByName(String name);

    Product getProductByDev(String dev);

    List<Product> getProductsByCategory(String category_name);

    Product saveProduct(Product product);

    Product updateProduct(Product product);

    void deleteProduct(Long id);

    void deleteProductByCategoryID(Long id);

    Boolean exist(String name);

    ProductDTO convertToDto(Product product);

    ProductDisplay convertToDisplay(ProductDTO dto);

    ProductDisplay convertEntToDisplay(Product product);

    List<ProductDTO> convertToDtoList(List<Product> productList);

    List<ProductDisplay> convertToDisplayList(List<ProductDTO> dtoList);

    List<ProductDisplay> convertEntToDisplayList(List<Product> productList);

    Product convertToEntity(ProductDTO productDTO) throws ParseException;
}
