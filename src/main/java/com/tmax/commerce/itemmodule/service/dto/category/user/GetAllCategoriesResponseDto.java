package com.tmax.commerce.itemmodule.service.dto.category.user;

import com.tmax.commerce.itemmodule.service.dto.category.CategoryDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetAllCategoriesResponseDto {
    private final Long id;
    private final String name = "전체";
    private final Long parentId;
    private final List<CategoryDto> subCategories;
}
