package com.example.review.repository;

import com.example.review.model.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
}
