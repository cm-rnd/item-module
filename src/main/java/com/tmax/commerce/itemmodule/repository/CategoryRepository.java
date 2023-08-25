package com.tmax.commerce.itemmodule.repository;

import com.tmax.commerce.itemmodule.entity.category.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends ExtendedRepository<Category, Long>, CategoryRepositoryCustom {

    List<Category> findByParentCategoryIsNull();
    List<Category> findByName(String name);

    Boolean existsByName(String name);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Category c WHERE c.shopId = :shopId AND c.name = :name")
    boolean existsByShopAndName(UUID shopId, String name);

    Slice<Category> findByShopId(UUID shopId, Pageable pageable);

    @Query("select max(c.sequence) from Category c WHERE c.shopId = :shopId")
    Optional<Integer> findMaxSequence(@Param("shopId") UUID shopId);

}
