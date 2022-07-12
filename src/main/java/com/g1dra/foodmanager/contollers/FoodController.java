package com.g1dra.foodmanager.contollers;

import com.g1dra.foodmanager.Repositories.FoodRepository;
import com.g1dra.foodmanager.models.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
public class FoodController {
    private final FoodRepository foodRepository;

    public FoodController(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @GetMapping
    public ResponseEntity<List<Food>> foods() {
        return new ResponseEntity<>(foodRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody Food food) {
        return new ResponseEntity<>(foodRepository.save(food),HttpStatus.CREATED);
    }
}
