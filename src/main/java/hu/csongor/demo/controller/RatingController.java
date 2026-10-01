package hu.csongor.demo.controller;
import hu.csongor.demo.dto.response.RatingResponse;
import hu.csongor.demo.entity.Rating;
import hu.csongor.demo.repository.RatingRepository;
import hu.csongor.demo.service.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/rating")
public class RatingController {
    private final RatingService ratingService;
    public  RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }
    @PostMapping
    public Object createRating(@RequestBody Rating rating){
        return ratingService.createRating(rating);
    }
    @GetMapping
    public List<RatingResponse> getAllRatings(){
        return ratingService.getAllRatings();
    }
    @GetMapping("/szakkor/{id}")
    public List<RatingResponse> getRatingsBySzakkorId(
            @PathVariable Integer id){

        return ratingService
                .getRatingsBySzakkorId(id);
    }
    @GetMapping("/atlag/{id}")
    public Double getAtlagErtekeles(
            @PathVariable Integer id){

        return ratingService
                .getAtlagErtekeles(id);
    }
    @DeleteMapping("/{id}")
    public String torles(
            @PathVariable Integer id){

        return ratingService.torles(id);
    }
    @GetMapping("/user/{id}")
    public List<RatingResponse> getUserRatings(
            @PathVariable Integer id){

        return ratingService
                .getUserRatings(id);
    }
}
