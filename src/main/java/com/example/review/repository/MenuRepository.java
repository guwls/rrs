package com.example.review.repository;

import com.example.review.model.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
}
