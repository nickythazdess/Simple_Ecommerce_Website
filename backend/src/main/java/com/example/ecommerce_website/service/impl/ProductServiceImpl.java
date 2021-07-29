package com.example.ecommerce_website.service.impl;

import com.example.ecommerce_website.displayDTO.ProductDisplay;
import com.example.ecommerce_website.dto.ProductDTO;
import com.example.ecommerce_website.entity.Category;
import com.example.ecommerce_website.entity.Product;
import com.example.ecommerce_website.repository.ProductRepository;
import com.example.ecommerce_website.service.CategoryService;
import com.example.ecommerce_website.service.ProductService;
import com.example.ecommerce_website.service.RatingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository repo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RatingService ratingService;

    public void setProductRepo(ProductRepository repo) { this.repo = repo; }

    public List<Product> getProductList() { return repo.findAll(); }

    public Optional<Product> getProduct(Long id) { return repo.findById(id); }

    public Product getProductByName(String name) { return repo.findProductByName(name); }

    public List<Product> searchProductByName(String name) { return repo.findByNameContainingIgnoreCase(name); }

    public Product getProductByDev(String dev) { return repo.findProductByDev(dev); }

    public List<Product> getProductsByCategory(String category_name) { return repo.findAllByCategory_name(category_name); }

    public Product saveProduct(Product product) { return repo.save(product); }

    public Product updateProduct(Product product) { return repo.save(product); }

    public void deleteProduct(Long id) { repo.delete(getProduct(id).get()); }

    public void deleteProductByCategoryID(Long cid) { repo.deleteAllByCategory_Id(cid); }

    public Boolean exist(String name) { return repo.existsByName(name); }

    public ProductDTO convertToDto(Product product){
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        productDTO.setCategory_name(product.getCategory().getName());
        productDTO.setImg_id(product.getImg());
        ratingService.getProductRating(product.getId());
        return productDTO;
    }

    public List<ProductDTO> convertToDtoList(List<Product> productList){
        List<ProductDTO> dtoList = new ArrayList<>();
        for (Product product : productList) {
            ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
            productDTO.setCategory_name(product.getCategory().getName());
            dtoList.add(productDTO);
        }
        return dtoList;
    }

    public Product convertToEntity(ProductDTO productDTO) throws ParseException {
        Product product = modelMapper.map(productDTO, Product.class);
        Category category = categoryService.getCategoryByName(productDTO.getCategory_name());
        product.setCategory(category);
        return product;
    }

    public ProductDisplay convertToDisplay(ProductDTO dto){
        ProductDisplay productDisplay = modelMapper.map(dto, ProductDisplay.class);
        if (dto.getImg_id() != null) productDisplay.setImg(dto.getImg_id().getData());
        productDisplay.setCategory(dto.getCategory_name());
        return productDisplay;
    }

    public List<ProductDisplay> convertToDisplayList(List<ProductDTO> dtoList){
        List<ProductDisplay> displayList = new ArrayList<>();
        for (ProductDTO dto : dtoList) {
            displayList.add(convertToDisplay(dto));
        }
        return displayList;
    }


    public ProductDisplay convertEntToDisplay(Product product) {
        ProductDisplay productDisplay = modelMapper.map(product, ProductDisplay.class);
        if (product.getImg() != null) productDisplay.setImg(product.getImg().getData());
        productDisplay.setCategory(product.getCategory().getName());
        productDisplay.setAvg_rate(ratingService.getProductAverageRating(product.getId()));
        return productDisplay;
    }

    public List<ProductDisplay> convertEntToDisplayList(List<Product> productList) {
        List<ProductDisplay> displayList = new ArrayList<>();
        for (Product product : productList) {
            displayList.add(convertEntToDisplay(product));
        }
        return displayList;
    }
}