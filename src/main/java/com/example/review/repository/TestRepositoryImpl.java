package com.example.review.repository;

import com.example.review.model.QTestEntity;
import com.example.review.model.TestEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class TestRepositoryImpl implements TestRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<TestEntity> findAllByNameByQuerydsl(String name) {
        return queryFactory.selectFrom(QTestEntity.testEntity)
                .fetch();
    }
}
