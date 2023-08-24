package com.tmax.commerce.itemmodule.service.dto.category.user;

import com.tmax.commerce.itemmodule.service.dto.category.OneDepthCategoryDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetAllOneDepthCategoriesResponseDto {
    public final List<OneDepthCategoryDto> categories;

}
