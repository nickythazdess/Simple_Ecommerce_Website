package com.example.ecommerce_website.service.impl;

import com.example.ecommerce_website.displayDTO.RatingDisplay;
import com.example.ecommerce_website.dto.RatingDTO;
import com.example.ecommerce_website.entity.Rating;
import com.example.ecommerce_website.entity.RatingID;
import com.example.ecommerce_website.repository.RatingRepository;
import com.example.ecommerce_website.service.AccountService;
import com.example.ecommerce_website.service.RatingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {
    @Autowired
    private RatingRepository repo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AccountService accountService;

    public void setRatingRepo(RatingRepository repo) { this.repo = repo; }

    public List<Rating> getRatingList() { return this.repo.findAll(); }

    public List<Rating> getUserRating(Long uid) { return this.repo.findAllById_Uid(uid); }

    public List<Rating> getProductRating(Long pid) { return repo.findAllById_Pid(pid); }

    public long getProductTotalNoRating(Long pid) { return repo.countById_Pid(pid); }

    public float getProductAverageRating(Long pid) {
        List<Rating> list = repo.findAllById_Pid(pid);
        int total = list.size();
        if (total == 0) return 0;
        long sum = 0;
        for (Rating rate : list) {
            sum += rate.getRate();
        }
        return sum/total;
    }

    public Rating getProductRatingOfUser(Long uid, Long pid) { return repo.findById(new RatingID(uid, pid)).get(); }

    public Rating addRating(Rating rating) { return repo.save(rating); }

    public Rating updateRating(Rating rating) { return repo.save(rating); }

    public void deleteRating(Long uid, Long pid) { repo.deleteById(new RatingID(uid, pid)); }

    public void cleanUpRatingWhenProductDelete(Long pid) { repo.deleteAllById_Pid(pid); }

    public void cleanUpRatingWhenAccountDelete(Long uid) { repo.deleteAllById_Uid(uid); }

    public RatingDTO convertToDto(Rating rating){
        RatingDTO ratingDTO = modelMapper.map(rating, RatingDTO.class);
        ratingDTO.setUid(rating.getId().getUid());
        ratingDTO.setPid(rating.getId().getPid());
        return ratingDTO;
    }

    public List<RatingDTO> convertToDtoList(List<Rating> ratingList){
        List<RatingDTO> dtoList = new ArrayList<>();
        for (Rating rating : ratingList) {
            dtoList.add(convertToDto(rating));
        }
        return dtoList;
    }

    public Rating convertToEntity(RatingDTO ratingDTO) throws ParseException {
        Rating rating = modelMapper.map(ratingDTO, Rating.class);
        rating.setId(new RatingID(ratingDTO.getUid(), ratingDTO.getPid()));
        return rating;
    }

    public RatingDisplay convertToDisplay(RatingDTO dto){
        RatingDisplay ratingDisplay = modelMapper.map(dto, RatingDisplay.class);
        ratingDisplay.setUsername(accountService.getAccount(dto.getUid()).get().getUsername());
        return ratingDisplay;
    }

    public List<RatingDisplay> convertToDisplayList(List<RatingDTO> dtoList){
        List<RatingDisplay> displayList = new ArrayList<>();
        for (RatingDTO dto : dtoList) {
            displayList.add(convertToDisplay(dto));
        }
        return displayList;
    }

    public RatingDisplay convertEntToDisplay(Rating rating) {
        RatingDisplay ratingDisplay = modelMapper.map(rating, RatingDisplay.class);
        ratingDisplay.setUsername(accountService.getAccount(rating.getId().getUid()).get().getUsername());
        return ratingDisplay;
    }

    public List<RatingDisplay> convertEntToDisplayList(List<Rating> ratingList) {
        List<RatingDisplay> displayList = new ArrayList<>();
        for (Rating rating : ratingList) {
            displayList.add(convertEntToDisplay(rating));
        }
        return displayList;
    }
}
