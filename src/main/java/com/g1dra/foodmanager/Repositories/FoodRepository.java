package com.g1dra.foodmanager.Repositories;

import com.g1dra.foodmanager.models.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends  JpaRepository<Food, Long> {
}
