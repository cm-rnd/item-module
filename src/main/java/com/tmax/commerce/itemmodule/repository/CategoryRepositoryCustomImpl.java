package com.tmax.commerce.itemmodule.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tmax.commerce.itemmodule.entity.category.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryCustomImpl implements CategoryRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    @Override
    public List<Category> findByNameContaining(String name) {
        return queryFactory
                .selectFrom(category)
                .where(category.name.startsWith(name))
                .orderBy(category.name.asc())
                .fetch();
    }

    public List<Category> findSubCategories(Category parentCategory) {

        return queryFactory.selectFrom(category)
                .where(category.parentCategory.eq(parentCategory))
                .fetch();
    }

    @Override
    public List<Category> findAllCategoryWithShoppingColor() {
        return queryFactory
                .select(category)
                .from(category)
                .innerJoin(category.shoppingColors, shoppingColor)
                .fetch();
    }
}
