package com.tmax.commerce.itemmodule.service.query.shop.owner.info;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryInfo {
    private final Long categoryId;
    private final String name;
    private final String description;
    private final int sequence;
    private final int itemCount;
}
