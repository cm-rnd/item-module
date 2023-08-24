package com.tmax.commerce.itemmodule.repository;

import com.tmax.commerce.itemmodule.entity.category.Category;

import java.util.List;

public interface CategoryRepositoryCustom {
    List<Category> findByNameContaining(String name);
    List<Category> findSubCategories(Category category);
    List<Category> findAllCategoryWithShoppingColor();
}
