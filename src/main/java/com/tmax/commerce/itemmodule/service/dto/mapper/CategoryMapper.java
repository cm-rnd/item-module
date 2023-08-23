package com.tmax.commerce.itemmodule.service.dto.mapper;

import com.tmax.commerce.itemmodule.entity.category.Category;
import com.tmax.commerce.itemmodule.service.dto.category.CategoriesByDepthDto;
import com.tmax.commerce.itemmodule.service.dto.category.CategoryDto;
import com.tmax.commerce.itemmodule.config.mapstruct.mapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = mapperConfig.class)
public interface CategoryMapper {
    @Mapping(target = "parentId", source = "parentCategory.id")
    @Mapping(target = "colorCode", source = "category.categoryColor.colorCode")
    CategoryDto toDto(Category category);

    @Mapping(target = "parentId", source = "parentCategory.id")
    @Mapping(target = "colorCode", source = "category.categoryColor.colorCode")
    CategoriesByDepthDto toCategoryByDepthDto(Category category);

    @Mapping(target = "parentCategory", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "categoryColor", ignore = true)
    @Mapping(target = "shoppingColors", ignore = true)
    Category toEntity(CategoryDto categoryDto);
}
