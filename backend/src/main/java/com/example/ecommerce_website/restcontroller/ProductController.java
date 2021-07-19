package com.example.ecommerce_website.restcontroller;

import com.example.ecommerce_website.dto.ProductDTO;
import com.example.ecommerce_website.entity.Product;
import com.example.ecommerce_website.exception.product.ProductExistedException;
import com.example.ecommerce_website.exception.product.ProductNotFoundException;
import com.example.ecommerce_website.service.ProductService;
import com.example.ecommerce_website.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private RatingService ratingService;

    @GetMapping()
    public ResponseEntity<List<ProductDTO>> getAll() {
        List<ProductDTO> dtoList = productService.convertToDtoList(productService.getProductList());
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    /*@GetMapping("/{id}")
    public Product findProduct(@PathVariable Long id) {
        Product product = ProductService.getProduct(id);
        if (product != null) return ca;
        else throw new ProductNotFoundException(id);
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        Optional<Product> product = productService.getProduct(id);
        if (!product.isPresent()) {
            throw new ProductNotFoundException(id);
        }
        ProductDTO dto = productService.convertToDto(product.get());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ProductDTO> getProductByName(@PathVariable String name) {
        Product product = productService.getProductByName(name);
        if (product == null) {
            throw new ProductNotFoundException(name);
        }
        ProductDTO dto = productService.convertToDto(product);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/category/{category_name}")
    //public ResponseEntity<List<Product>> findProductByCategory(@RequestParam(value="category") String category_name) {
    public ResponseEntity<List<ProductDTO>> getProductByCategory(@PathVariable String category_name) {
        /*List<Product> productList = productService.getProductList();
        List<ProductDTO> dtoList = convertToDtoList(productList);
        List<ProductDTO> filteredDTOList = dtoList.stream()
                .filter(productDTO -> category_name.equals(productDTO.getCategory_name()))
                .collect(Collectors.toList());*/
        return new ResponseEntity<>(productService.convertToDtoList(productService.getProductsByCategory(category_name)), HttpStatus.OK);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    public ProductDTO createProduct(@Valid @RequestBody(required = true) ProductDTO productDTO) throws ParseException {
        if (productService.exist(productDTO.getName()))
            throw new ProductExistedException(productDTO.getName());
        productService.saveProduct(productService.convertToEntity(productDTO));
        return productDTO;
    }

    @PutMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    public ProductDTO updateProduct(@Valid @RequestBody(required = true) ProductDTO productUpdate) throws ParseException {
        Product product = productService.convertToEntity(productUpdate);
        productService.updateProduct(product);
        return productUpdate;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HashMap<String, String> deleteProduct(@PathVariable Long id) {
        Optional<Product> product = productService.getProduct(id);
        if (!product.isPresent()) {
            throw new ProductNotFoundException(id);
        }
        productService.deleteProduct(id);
        ratingService.cleanUpRatingWhenProductDelete(id);
        HashMap<String, String> map = new HashMap<>();
        map.put("message", "Delete successfully!");
        return map;
    }
}
