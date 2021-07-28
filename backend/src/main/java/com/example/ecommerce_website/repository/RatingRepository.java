package com.example.ecommerce_website.repository;

import com.example.ecommerce_website.entity.Rating;
import com.example.ecommerce_website.entity.RatingID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, RatingID> {
    List<Rating> findAllById_Uid(Long uid);

    List<Rating> findAllById_Pid(Long pid);

    Rating findAllById_UidAndId_Pid(Long uid, Long pid);

    void deleteAllById_Pid(Long pid);

    void deleteAllById_Uid(Long pid);

    long countById_Pid(Long pid);
}
