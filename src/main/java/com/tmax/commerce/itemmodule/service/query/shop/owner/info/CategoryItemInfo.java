package com.tmax.commerce.itemmodule.service.query.shop.owner.info;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
@Getter
@AllArgsConstructor
public class CategoryItemInfo {
    private final Long categoryId;
    private final String name;
    private final String description;
    private final int sequence;
    private final int itemCount;
    private final List<SimpleItemInfo> itemInfos;

    @Getter
    @AllArgsConstructor
    public static class SimpleItemInfo {
        private final Long itemGroupId;
        private final String name;
        private final String description;
        private final int sequence;
    }
}
