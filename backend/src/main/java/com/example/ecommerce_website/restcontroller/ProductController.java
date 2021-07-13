package com.example.ecommerce_website.restcontroller;

import com.example.ecommerce_website.dto.ProductDTO;
import com.example.ecommerce_website.entity.Category;
import com.example.ecommerce_website.entity.Product;
import com.example.ecommerce_website.exception.ProductNotFoundException;
import com.example.ecommerce_website.service.CategoryService;
import com.example.ecommerce_website.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ModelMapper modelMapper;

    private ProductDTO convertToDto(Product product){
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        productDTO.setCategory_name(product.getCategory().getName());
        return productDTO;
    }

    private List<ProductDTO> convertToDtoList(List<Product> productList){
        List<ProductDTO> dtoList = new ArrayList<>();
        for (Product product : productList) {
            ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
            productDTO.setCategory_name(product.getCategory().getName());
            dtoList.add(productDTO);
        }
        return dtoList;
    }

    private Product convertToEntity(ProductDTO bookDTO) throws ParseException {
        Product product = modelMapper.map(bookDTO, Product.class);
        Category category = categoryService.getCategoryByName(bookDTO.getCategory_name());
        product.setCategory(category);
        return product;
    }

    @GetMapping()
    public ResponseEntity<List<ProductDTO>> findAll() {
        List<ProductDTO> dtoList = convertToDtoList(productService.getProductList());
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    /*@GetMapping("/{id}")
    public Product findProduct(@PathVariable Long id) {
        Product product = ProductService.getProduct(id);
        if (product != null) return ca;
        else throw new ProductNotFoundException(id);
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findProduct(@PathVariable Long id) {
        Optional<Product> product = productService.getProduct(id);
        if (!product.isPresent()) {
            throw new ProductNotFoundException(id);
        }
        ProductDTO dto = convertToDto(product.get());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ProductDTO> findProductByName(@PathVariable String name) {
        Product product = productService.getProductByName(name);
        if (product == null) {
            throw new ProductNotFoundException(name);
        }
        ProductDTO dto = convertToDto(product);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/in{category_name}")
    //public ResponseEntity<List<Product>> findProductByCategory(@RequestParam(value="category") String category_name) {
    public ResponseEntity<List<ProductDTO>> findProductByCategory(@PathVariable String category_name) {
        List<Product> productList = productService.getProductList();
        List<ProductDTO> dtoList = convertToDtoList(productList);
        List<ProductDTO> filteredDTOList = dtoList.stream()
                .filter(productDTO -> category_name.equals(productDTO.getCategory_name()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(filteredDTOList, HttpStatus.OK);
    }

    @PostMapping()
    @ResponseBody
    public ProductDTO saveProduct(@Valid @RequestBody(required = true) ProductDTO productDTO) throws ParseException {
        productService.saveProduct(convertToEntity(productDTO));
        return productDTO;
    }

    @PutMapping()
    @ResponseBody
    public ProductDTO updateProduct(@Valid @RequestBody(required = true) ProductDTO productUpdate) throws ParseException {
        Product product = convertToEntity(productUpdate);
        productService.updateProduct(product);
        return productUpdate;
    }

    @DeleteMapping("/{id}")
    public HashMap<String, String> deleteProduct(@PathVariable Long id) {
        Optional<Product> product = productService.getProduct(id);
        if (!product.isPresent()) {
            throw new ProductNotFoundException(id);
        }
        productService.deleteProduct(id);
        HashMap<String, String> map = new HashMap<>();
        map.put("message", "Delete successfully!");
        return map;
    }
}
