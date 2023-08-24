package com.tmax.commerce.itemmodule.service.dto.category.master;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostCategoryResponseDto {
    private Long id;
    private String name;
}