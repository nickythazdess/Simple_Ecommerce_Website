package com.example.ecommerce_website.restcontroller;

import com.example.ecommerce_website.dto.RatingDTO;
import com.example.ecommerce_website.entity.Product;
import com.example.ecommerce_website.entity.Rating;
import com.example.ecommerce_website.exception.product.ProductNotFoundException;
import com.example.ecommerce_website.service.impl.UserDetailsImpl;
import com.example.ecommerce_website.service.impl.UserDetailsServiceImpl;
import com.example.ecommerce_website.service.ProductService;
import com.example.ecommerce_website.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api/rating")
public class RatingController {
    @Autowired
    RatingService ratingService;
    @Autowired
    UserDetailsServiceImpl userService;
    @Autowired
    ProductService productService;

    @GetMapping("/admin/all")
    public ResponseEntity<?> getRatingList(){
        return ResponseEntity.ok().body(ratingService.convertToDtoList(ratingService.getRatingList()));
    }

    @GetMapping("/admin/user/{uid}")
    public ResponseEntity<?> getUserRatings(@PathVariable Long uid){
        return ResponseEntity.ok().body(ratingService.convertToDtoList(ratingService.getUserRating(uid)));
    }

    @GetMapping("/product/{pid}")
    public ResponseEntity<?> getProductRatings(@PathVariable Long pid){
        if (productService.getProduct(pid).isEmpty()) throw new ProductNotFoundException(pid);
        else return ResponseEntity.ok().body(ratingService.convertToDtoList(ratingService.getProductRating(pid)));
    }

    @GetMapping("/avg/product/{pid}")
    public ResponseEntity<?> getProductAverageRating(@PathVariable Long pid){
        if (productService.getProduct(pid).isEmpty()) throw new ProductNotFoundException(pid);
        else {
            List<RatingDTO> list = ratingService.convertToDtoList(ratingService.getProductRating(pid));
            int total = list.size();
            long sum = 0;
            for (RatingDTO rate : list) {
                sum += rate.getRate();
            }
            return ResponseEntity.ok().body(sum / total);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getProductRatingOfUSer(@RequestParam(name="uid") Long uid, @RequestParam(name="pid") Long pid) {
        if (productService.getProduct(pid).isEmpty()) throw new ProductNotFoundException(pid);
        return ResponseEntity.ok().body(ratingService.convertToDto(ratingService.getProductRatingOfUser(uid, pid)));
    }

    @PostMapping()
    public ResponseEntity<?> createRating(@RequestBody RatingDTO ratingDTO) throws ParseException {
        Optional<Product> product = productService.getProduct(ratingDTO.getPid());
        if (product.isEmpty())
            throw new ProductNotFoundException(product.get().getId());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = ((UserDetailsImpl)auth.getPrincipal());
        ratingDTO.setUid(userDetails.getId());
        ratingDTO.setDate(LocalDate.now());
        return ResponseEntity.ok().body(ratingService.addRating(ratingService.convertToEntity(ratingDTO)));
    }

    @PutMapping()
    public ResponseEntity<?> updateRating(@RequestBody RatingDTO ratingDTO) {
        Optional<Product> product = productService.getProduct(ratingDTO.getPid());
        if (product.isEmpty())
            throw new ProductNotFoundException(product.get().getId());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = ((UserDetailsImpl)auth.getPrincipal());
        Rating rating = ratingService.getProductRatingOfUser(userDetails.getId(), ratingDTO.getPid());
        if (ratingDTO.getRate() >= 0) rating.setRate(ratingDTO.getRate());
        if (ratingDTO.getCmt() != null) rating.setCmt(ratingDTO.getCmt());

        ratingDTO.setDate(LocalDate.now());
        return ResponseEntity.ok().body(ratingService.updateRating(rating));
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteRating(@RequestBody RatingDTO ratingDTO) {
        Optional<Product> product = productService.getProduct(ratingDTO.getPid());
        if (product.isEmpty())
            throw new ProductNotFoundException(product.get().getId());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = ((UserDetailsImpl)auth.getPrincipal());
        ratingService.deleteRating(userDetails.getId(), ratingDTO.getPid());
        return ResponseEntity.ok().body(String.format("Delete successful"));
    }
}
