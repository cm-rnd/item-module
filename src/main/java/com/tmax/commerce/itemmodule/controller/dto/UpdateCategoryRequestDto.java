package com.tmax.commerce.itemmodule.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateCategoryRequestDto {
    private Long categoryId;
    private String name;
}