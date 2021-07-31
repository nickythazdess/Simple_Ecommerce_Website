package com.example.ecommerce_website.restcontroller;

import com.example.ecommerce_website.dto.ProductDTO;
import com.example.ecommerce_website.entity.Account;
import com.example.ecommerce_website.entity.Image;
import com.example.ecommerce_website.entity.Product;
import com.example.ecommerce_website.exception.account.AccountNotFoundException;
import com.example.ecommerce_website.exception.product.ProductExistedException;
import com.example.ecommerce_website.exception.product.ProductNotFoundException;
import com.example.ecommerce_website.service.CategoryService;
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
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private RatingService ratingService;

    @GetMapping("/admin")
    public ResponseEntity<?> adminGetAll() {
        return ResponseEntity.ok().body(productService.convertToDtoList(productService.getProductList()));
    }

    // Public space

    @GetMapping()
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body(productService.convertEntToDisplayList(productService.getProductList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        Optional<Product> product = productService.getProduct(id);
        if (!product.isPresent()) {
            throw new ProductNotFoundException(id);
        }
        return ResponseEntity.ok().body(productService.convertEntToDisplay(product.get()));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getProductByName(@PathVariable String name) {
        Product product = productService.getProductByName(name);
        if (product == null) {
            throw new ProductNotFoundException(name);
        }
        return ResponseEntity.ok().body(productService.convertToDto(product));
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchProductByName(@PathVariable String name) {
        List<Product> productList = productService.searchProductByName(name);
        return ResponseEntity.ok().body(productService.convertEntToDisplayList(productList));
    }

    @GetMapping("/category/{category_name}")
    public ResponseEntity<?> getProductByCategory(@PathVariable String category_name) {
        return ResponseEntity.ok().body(productService.convertEntToDisplayList(productService.getProductsByCategory(category_name)));
    }

    // Admin space

    @GetMapping("/admin/{id}")
    public ResponseEntity<?> adminGetProduct(@PathVariable Long id) {
        Optional<Product> product = productService.getProduct(id);
        if (!product.isPresent()) {
            throw new ProductNotFoundException(id);
        }
        ProductDTO dto = productService.convertToDto(product.get());
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/admin/name/{name}")
    public ResponseEntity<?> adminGetProductByName(@PathVariable String name) {
        Product product = productService.getProductByName(name);
        if (product == null) {
            throw new ProductNotFoundException(name);
        }
        ProductDTO dto = productService.convertToDto(product);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/admin/category/{category_name}")
    public ResponseEntity<?> adminGetProductByCategory(@PathVariable String category_name) {
        return ResponseEntity.ok().body(productService.convertToDtoList(productService.getProductsByCategory(category_name)));
    }

    @PostMapping("/admin")
    @ResponseBody
    public ResponseEntity<?> createProduct(@Valid @RequestBody(required = true) ProductDTO productDTO, @RequestParam(name="file", required=false) MultipartFile file) throws ParseException {
        if (productService.exist(productDTO.getName()))
            throw new ProductExistedException(productDTO.getName());
        productDTO.setCreatedDate(LocalDate.now());
        productDTO.setUpdatedDate(LocalDate.now());
        if (!Objects.isNull(file)) try {
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
    public ResponseEntity<?> updateProduct(@RequestBody(required = true) ProductDTO productUpdate, @RequestParam(name="file", required=false) MultipartFile file) {
        Optional<Product> productTemp = productService.getProduct(productUpdate.getId());
        if (!productTemp.isPresent()) throw new ProductNotFoundException(productUpdate.getId());
        Product product = productTemp.get();

        if (productUpdate.getName() != null) product.setName(productUpdate.getName());
        if (!Objects.isNull(productUpdate.getCategory_name())) product.setCategory(categoryService.getCategoryByName(productUpdate.getCategory_name()));
        if (productUpdate.getPrice() >=0) product.setPrice(productUpdate.getPrice());
        if (!Objects.isNull(productUpdate.getDev())) product.setDev(productUpdate.getDev());
        if (!Objects.isNull(productUpdate.getDescription())) product.setDescription(product.getDescription());

        if (!Objects.isNull(file)) try {
            if (!Objects.isNull(product.getImg())) {
                Image image = imageService.getImageById(product.getImg().getId()).get();
                image.setData(Base64.getEncoder().encodeToString(file.getBytes()));
                image.setContentType(file.getContentType());
                image.setSize(file.getSize());
                imageService.saveImage(image);
            }
            Image image = new Image(Base64.getEncoder().encodeToString(file.getBytes()), product.getName(), file.getContentType(), file.getSize());
            imageService.saveImage(image);
            product.setImg(image);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Could not upload the image: %s!", file.getOriginalFilename()));
        }

        product.setUpdatedDate(LocalDate.now());
        productService.updateProduct(product);
        return ResponseEntity.ok().body(productService.convertToDto(product));
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        Optional<Product> product = productService.getProduct(id);
        if (!product.isPresent()) {
            throw new ProductNotFoundException(id);
        }
        productService.deleteProduct(id);
        ratingService.cleanUpRatingWhenProductDelete(id);
        return ResponseEntity.ok().body(String.format("Delete successful"));
    }
}
