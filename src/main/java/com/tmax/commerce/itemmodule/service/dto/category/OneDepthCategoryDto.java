package com.tmax.commerce.itemmodule.service.dto.category;

import lombok.Getter;

import java.util.List;
@Getter

public class OneDepthCategoryDto {
    private final Long id;
    private final String name;
    private final Long parentId;
    private final String colorCode;
    private final List<CategoriesByDepthDto> subCategories;

    public OneDepthCategoryDto(Long id, String name, Long parentId, String colorCode, List<CategoriesByDepthDto> subCategories) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.colorCode = colorCode;
        this.subCategories = subCategories;
    }
}
