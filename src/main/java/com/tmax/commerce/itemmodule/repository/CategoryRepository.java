package com.tmax.commerce.itemmodule.repository;

import com.tmax.commerce.itemmodule.entity.category.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends ExtendedRepository<Category, Long>, CategoryRepositoryCustom {

    List<Category> findByParentCategoryIsNull();

    List<Category> findByName(String name);

    Boolean existsByName(String name);

    Optional<Category> findByUuid(UUID uuid);
}
