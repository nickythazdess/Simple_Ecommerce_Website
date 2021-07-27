package com.example.ecommerce_website.restcontroller;

import com.example.ecommerce_website.dto.ProductDTO;
import com.example.ecommerce_website.entity.Image;
import com.example.ecommerce_website.entity.Product;
import com.example.ecommerce_website.exception.product.ProductExistedException;
import com.example.ecommerce_website.exception.product.ProductNotFoundException;
import com.example.ecommerce_website.service.ImageService;
import com.example.ecommerce_website.service.ProductService;
import com.example.ecommerce_website.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private RatingService ratingService;

    @GetMapping()
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body(productService.convertToDtoList(productService.getProductList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        Optional<Product> product = productService.getProduct(id);
        if (!product.isPresent()) {
            throw new ProductNotFoundException(id);
        }
        ProductDTO dto = productService.convertToDto(product.get());
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getProductByName(@PathVariable String name) {
        Product product = productService.getProductByName(name);
        if (product == null) {
            throw new ProductNotFoundException(name);
        }
        ProductDTO dto = productService.convertToDto(product);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/category/{category_name}")
    public ResponseEntity<?> getProductByCategory(@PathVariable String category_name) {
        return ResponseEntity.ok().body(productService.convertToDtoList(productService.getProductsByCategory(category_name)));
    }

    @PostMapping("/admin")
    @ResponseBody
    public ResponseEntity<?> createProduct(@Valid @RequestBody(required = true) ProductDTO productDTO, @RequestParam(name="file", required=false) MultipartFile file) throws ParseException {
        if (productService.exist(productDTO.getName()))
            throw new ProductExistedException(productDTO.getName());
        productDTO.setCreatedDate(LocalDate.now());
        productDTO.setUpdatedDate(LocalDate.now());
        if (!file.isEmpty()) try {
            Image image = new Image(Base64.getEncoder().encodeToString(file.getBytes()), productDTO.getName(), file.getContentType(), file.getSize());
            imageService.saveImage(image);
            productDTO.setImg_id(image);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Could not upload the image: %s!", file.getOriginalFilename()));
        }
        productService.saveProduct(productService.convertToEntity(productDTO));
        return ResponseEntity.ok().body(productDTO);
    }

    @PutMapping("/admin")
    @ResponseBody
    public ProductDTO updateProduct(@Valid @RequestBody(required = true) ProductDTO productUpdate) throws ParseException {
        Product product = productService.convertToEntity(productUpdate);
        productUpdate.setUpdatedDate(LocalDate.now());
        productService.updateProduct(product);
        return productUpdate;
    }

    @DeleteMapping("/admin/{id}")
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
