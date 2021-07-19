package com.example.ecommerce_website.service;

import com.example.ecommerce_website.dto.RatingDTO;
import com.example.ecommerce_website.entity.Rating;
import com.example.ecommerce_website.entity.RatingID;
import com.example.ecommerce_website.repository.RatingRepository;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public interface RatingService {
    void setRatingRepo(RatingRepository repo);

    List<Rating> getRatingList();

    List<Rating> getUserRating(Long uid);

    List<Rating> getProductRating(Long pid);

    Rating getProductRatingOfUser(Long uid, Long pid);

    Rating addRating(Rating rating);

    Rating updateRating(Rating rating);

    void deleteRating(Long uid, Long pid);

    void cleanUpRatingWhenProductDelete(Long pid);

    void cleanUpRatingWhenAccountDelete(Long uid);

    RatingDTO convertToDto(Rating rating);

    List<RatingDTO> convertToDtoList(List<Rating> ratingList);

    Rating convertToEntity(RatingDTO ratingDTO) throws ParseException;
}
