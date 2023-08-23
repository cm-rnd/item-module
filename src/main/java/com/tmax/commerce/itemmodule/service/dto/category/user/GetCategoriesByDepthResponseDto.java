package com.tmax.commerce.itemmodule.service.dto.category.user;

import com.tmax.commerce.itemmodule.service.dto.category.CategoriesByDepthDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Getter
@Builder
public class GetCategoriesByDepthResponseDto {
    private final List<CategoriesByDepthDto> categories;

}
