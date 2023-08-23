package com.tmax.commerce.itemmodule.controller.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ConnectProductsWithCategoryRequestDto {
    private Long categoryId;
    private List<Long> productsIds;
}
