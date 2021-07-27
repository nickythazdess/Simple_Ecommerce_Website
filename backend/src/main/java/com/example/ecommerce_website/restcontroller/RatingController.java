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
    public List<RatingDTO> getRatingList(){
        return ratingService.convertToDtoList(ratingService.getRatingList());
    }

    @GetMapping("/admin/user/{uid}")
    public List<RatingDTO> getUserRatings(@PathVariable Long uid){
        return ratingService.convertToDtoList(ratingService.getUserRating(uid));
    }

    @GetMapping("/product/{pid}")
    public List<RatingDTO> getProductRatings(@PathVariable Long pid){
        if (productService.getProduct(pid).isEmpty()) throw new ProductNotFoundException(pid);
        else return ratingService.convertToDtoList(ratingService.getProductRating(pid));
    }

    @GetMapping("/avg/product/{pid}")
    public List<RatingDTO> getProductAverageRating(@PathVariable Long pid){
        if (productService.getProduct(pid).isEmpty()) throw new ProductNotFoundException(pid);
        else return ratingService.convertToDtoList(ratingService.getProductRating(pid));
    }

    @GetMapping("/user")
    public RatingDTO getProductRatingOfUSer(@RequestParam(name="uid") Long uid, @RequestParam(name="pid") Long pid){
        if (productService.getProduct(pid).isEmpty()) throw new ProductNotFoundException(pid);
        return ratingService.convertToDto(ratingService.getProductRatingOfUser(uid, pid));
    }

    @PostMapping("/user")
    public Rating createRating(@RequestBody RatingDTO ratingDTO) throws ParseException {
        Optional<Product> product = productService.getProduct(ratingDTO.getPid());
        if (product.isEmpty())
            throw new ProductNotFoundException(product.get().getId());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = ((UserDetailsImpl)auth.getPrincipal());
        ratingDTO.setUid(userDetails.getId());
        ratingDTO.setDate(LocalDate.now());
        return ratingService.addRating(ratingService.convertToEntity(ratingDTO));
    }
}
