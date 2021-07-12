package com.example.ecommerce_website.restcontroller;

import com.example.ecommerce_website.entity.Product;
import com.example.ecommerce_website.exception.ProductNotFoundException;
import com.example.ecommerce_website.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    /*@GetMapping()
    public List<Product> findAll() {
        return ProductService.getProductList();
    }*/

    @GetMapping()
    public ResponseEntity<List<Product>> findAll() {
        return new ResponseEntity<>(productService.getProductList(), HttpStatus.OK);
    }

    /*@GetMapping("/{id}")
    public Product findProduct(@PathVariable Long id) {
        Product product = ProductService.getProduct(id);
        if (product != null) return ca;
        else throw new ProductNotFoundException(id);
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<Product> findProduct(@PathVariable Long id) {
        Optional<Product> product = productService.getProduct(id);
        if (!product.isPresent()) {
            throw new ProductNotFoundException(id);
        }
        return new ResponseEntity<>(product.get(), HttpStatus.OK);
    }

    @GetMapping("/in{category_name}")
    public ResponseEntity<List<Product>> findProductByCategory(@PathVariable String category_name) {
        List<Product> productList = productService.getProductList();
        List<Product> filteredList = productList.stream()
                .filter(product -> category_name.equals(product.getCategory_id().getName()))
                .collect(Collectors.toList());
        if (filteredList.isEmpty()) {

        }
        return new ResponseEntity<>(filteredList, HttpStatus.OK);
    }

    /*@GetMapping()
    public ResponseEntity<List<Product>> findProductByCategory(@RequestParam(value="category") String category_name) {
        List<Product> productList = productService.getProductList();
        List<Product> filteredList = productList.stream()
                .filter(product -> category_name.equals(product.getCategory_id().getName()))
                .collect(Collectors.toList());
        if (filteredList.isEmpty()) {

        }
        return new ResponseEntity<>(filteredList, HttpStatus.OK);
    }*/

    @PostMapping()
    public Product saveProduct(@Valid @RequestBody Product product) { return productService.saveProduct(product); }

    @PutMapping()
    public HashMap<String, String> updateProduct(@Valid @RequestBody Product ProductUpdate) {
        productService.updateProduct(ProductUpdate);
        HashMap<String, String> map = new HashMap<>();
        map.put("message", "Update successfully!");
        return map;
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
