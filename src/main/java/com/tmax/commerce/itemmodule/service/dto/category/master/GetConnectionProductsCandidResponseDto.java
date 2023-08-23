package com.tmax.commerce.itemmodule.service.dto.category.master;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class GetConnectionProductsCandidResponseDto {
    @Builder.Default
    private List<Product> products = new ArrayList<>();

    @Getter
    @Builder
    public static class Product {
        private Long id;
        private String name;
        private String categoryName;
        private String sellerName;
        private Long sellingPrice;
        private String sellingStatus;
    }
}
