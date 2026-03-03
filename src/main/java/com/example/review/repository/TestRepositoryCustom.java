package com.example.review.repository;

import com.example.review.model.TestEntity;

import java.util.List;

public interface TestRepositoryCustom {

    public List<TestEntity> findAllByNameByQuerydsl(String name);
}
