package com.example.review.repository;

import com.example.review.model.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRepository extends JpaRepository<TestEntity, Long>, TestRepositoryCustom {

    public List<TestEntity> findAllByName(String name);
}
