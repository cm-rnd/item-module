package com.tmax.commerce.itemmodule.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostCategoryRequestDto {
    public static final String NEW_CATEGORY_NAME = "새 카테고리";
    private Long parentCategoryId;
}

