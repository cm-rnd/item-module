package com.tmax.commerce.itemmodule.service.dto.category;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CategoriesByDepthDto {
    private final Long id;
    private final String name;
    private final Long parentId;
    private final String colorCode;
}
