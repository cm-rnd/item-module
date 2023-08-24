package com.tmax.commerce.itemmodule.service.dto.category.user;

import com.tmax.commerce.itemmodule.service.dto.category.CategoryDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Getter
@Builder

public class GetSubcategoriesResponseDto {
    private final List<CategoryDto> subCategories;
}
